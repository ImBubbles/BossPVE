package me.bubbles.bosspve;

import com.onarandombox.MultiverseCore.MultiverseCore;
import me.bubbles.bosspve.commands.manager.CommandManager;
import me.bubbles.bosspve.configs.ConfigManager;
import me.bubbles.bosspve.entities.manager.EntityManager;
import me.bubbles.bosspve.events.manager.EventManager;
import me.bubbles.bosspve.game.manager.GameManager;
import me.bubbles.bosspve.items.manager.ItemManager;
import me.bubbles.bosspve.stages.Stage;
import me.bubbles.bosspve.stages.StageManager;
import me.bubbles.bosspve.ticker.Ticker;
import me.bubbles.bosspve.ticker.TimerManager;
import me.bubbles.bosspve.utility.*;
import me.bubbles.bosspve.utility.string.UtilString;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.time.Instant;
import java.util.logging.Level;

public final class BossPVE extends JavaPlugin {

    // INSTANCE
    private static BossPVE INSTANCE;

    // TIMER & TICKER
    private TimerManager timerManager;
    private Ticker ticker;

    // MANAGERS
    private ConfigManager configManager;
    private EntityManager entityManager;
    private CommandManager commandManager;
    private EventManager eventManager;
    private StageManager stageManager;
    private ItemManager itemManager;
    private GameManager gameManager;

    // APIS
    private Economy economy;
    private MultiverseCore multiverseCore;

    public static BossPVE getInstance() {
        return INSTANCE;
    }

    @Override
    public void onEnable() {

        INSTANCE=this;

        // Config
        configManager=new ConfigManager();
        getConfig().options().copyDefaults();
        saveDefaultConfig();
        configManager.addConfig(
                "config.yml",
                "stages.yml"
        );

        // PREPARE UTIL
        new UtilDatabase();

        // MANAGERS
        // THIS ORDER IS VERY IMPORTANT, SWAPPING THINGS AROUND WILL CAUSE VALUES TO BE RETURNED AS NULL
        if(!setupEconomy()) {
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }
        if(!setupMultiverse()) {
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }
        if(!setupPAPI()) {
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }

        timerManager=new TimerManager();
        itemManager=new ItemManager();
        itemManager.initEnchants();
        entityManager=new EntityManager();
        gameManager=new GameManager();
        eventManager=new EventManager();
        // Moved to ServerLoadEvent VVVV
        // initStageManager();
        commandManager=new CommandManager();

        // Ticker
        ticker=new Ticker(this::onTick, true);

        // XP Bar
        timerManager.addTimer(new UpdateXP());
        // CACHE
        timerManager.addTimer(new JustInCase());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        saveUserData();
        if(stageManager!=null) {
            stageManager.getStages().forEach(stage -> {
                stage.setEnabled(false);
                stage.removeAll();
                World world = stage.getSpawn().getWorld();
                if(world!=null) {
                    world.save();
                }
            });
        }
    }

    public void saveUserData() {
        for(Player player : Bukkit.getOnlinePlayers()) {
            UtilUserData uud = getGameManager().getGamePlayer(player.getUniqueId()).getCache();
            UtilUserData.saveSync(uud);
        }
    }

    // ON TICK
    public void onTick() {
        timerManager.onTick();
        itemManager.onTick();
    }

    // RELOAD CFG
    public void reload() {
        getStageManager().setSpawningAll(false);
        getConfigManager().reloadAll();
        initStageManager();
    }

    // STAGE MANAGER
    public void initStageManager() {
        if(stageManager!=null) {
            stageManager.getStages().forEach(stage -> stage.setEnabled(false));
            stageManager.getStages().forEach(Stage::killAll);
        }
        stageManager=new StageManager(configManager.getConfig("stages.yml"));
    }

    // VAULT
    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            getLogger().log(Level.SEVERE, "NO VAULT PLUGIN");
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            getLogger().log(Level.SEVERE, "NO ECONOMY SUPPORT");
            return false;
        }
        economy = rsp.getProvider();
        return economy != null;
    }

    // Worlds
    private boolean setupMultiverse() {
        multiverseCore = (MultiverseCore) getServer().getPluginManager().getPlugin("Multiverse-Core");
        if (multiverseCore == null) {
            getLogger().log(Level.SEVERE, "No Multiverse-Core");
            return false;
        }
        return true;
    }

    // PlaceholderAPI
    private boolean setupPAPI() {
        if(Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            new PAPI().register();
            return true;
        }
        return false;
    }

    // GETTERS
    public long getEpochTimestamp() {
        return Instant.now().getEpochSecond();
    }

    public TimerManager getTimerManager() {
        return timerManager;
    }

    public ItemManager getItemManager() {
        return itemManager;
    }

    public ConfigManager getConfigManager() {
        return configManager;
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }

    public CommandManager getCommandManager() {
        return commandManager;
    }

    public StageManager getStageManager() {
        return stageManager;
    }

    public EventManager getEventManager() {
        return eventManager;
    }

    public MultiverseCore getMultiverseCore() {
        return multiverseCore;
    }

    public Economy getEconomy() {
        return economy;
    }

    public GameManager getGameManager() {
        return gameManager;
    }

}
