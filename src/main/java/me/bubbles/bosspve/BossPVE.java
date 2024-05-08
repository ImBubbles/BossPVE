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
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.time.Instant;
import java.util.logging.Level;

public final class BossPVE extends JavaPlugin {

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

    @Override
    public void onEnable() {

        // Config
        configManager=new ConfigManager(this);
        getConfig().options().copyDefaults();
        saveDefaultConfig();
        configManager.addConfig(
                "config.yml",
                "stages.yml"
        );

        // PREP UTILITY
        new UtilDatabase(this);
        new UtilString(this);
        new UtilCalculator(this);

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
        itemManager=new ItemManager(this);
        itemManager.initEnchants();
        entityManager=new EntityManager(this);
        gameManager=new GameManager(this);
        eventManager=new EventManager(this);
        // Moved to ServerLoadEvent VVVV
        // initStageManager();
        commandManager=new CommandManager(this);

        // Ticker
        ticker=new Ticker(this);
        ticker.setEnabled(true);

        // XP Bar
        timerManager.addTimer(new UpdateXP(this));
        timerManager.addTimer(new JustInCase(this));
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        saveUserData();
        if(stageManager!=null) {
            stageManager.getStages().forEach(stage -> stage.setEnabled(false));
            stageManager.getStages().forEach(Stage::killAll);
        }
    }

    public void saveUserData() {
        for(Player player : Bukkit.getOnlinePlayers()) {
            UtilUserData uud = getGameManager().getGamePlayer(player.getUniqueId()).getCache();
            UtilUserData.save(this, uud);
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
        stageManager=new StageManager(this,configManager.getConfig("stages.yml"));
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
            new PAPI(this).register();
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
