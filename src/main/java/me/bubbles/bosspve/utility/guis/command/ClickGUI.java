package me.bubbles.bosspve.utility.guis.command;

import me.bubbles.bosspve.BossPVE;
import me.bubbles.bosspve.events.presets.GuiClickIndex;
import me.bubbles.bosspve.utility.UtilNumber;
import me.bubbles.bosspve.utility.guis.GUI;
import me.bubbles.bosspve.utility.pagifier.Gridifier;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.InventoryHolder;

public abstract class ClickGUI<T> extends GUI implements IClickGUI<T> {

    private final T[] array;
    private int indexCap;
    protected int page;
    private boolean fullSize;
    private Gridifier<T> gridifier;

    public ClickGUI(BossPVE plugin, InventoryHolder holder, Class<T> clazz, T[] array, int page, boolean fullSize) {
        super(plugin, holder, InventoryType.CHEST);
        this.array = array;
        this.fullSize=fullSize;
        int rows = fullSize ? 5 : 2;
        this.gridifier = new Gridifier<>(clazz, array, rows, 9);
        this.indexCap = fullSize ? 45 : 18;
        setPage(page);
    }

    public Gridifier<T> getGridifier() {
        return gridifier;
    }

    public void setPage(int page) {
        this.page=(int) UtilNumber.clampBorder(gridifier.getTotalPages()-1, 0, page);
        buildPage();
    }

    private void buildPage() {

        for(int i=0; i<indexCap; i++) {

            int completeIndex = ((indexCap)*page+i)+1;

            if(array.length<completeIndex) {
                break;
            }

            set(i, getItemStack(array[completeIndex-1]), getGuiClick(array[completeIndex-1], i));

        }

        if(getBackItemStack()!=null&&getBackCommand(0)!=null) {
            int index = fullSize ? 44 : 18;
            if(page>0) {
                set(index, getBackItemStack(), getBackCommand(index));
            } else if(getBottomItemStack()!=null) {
                set(index, getBottomItemStack(), new GuiClickIndex(plugin, inventory, index, false));
            }
        }

        if(getForwardItemStack()!=null&&getForwardCommand(0)!=null) {
            int index = fullSize ? 53 : 26;
            if(page<(gridifier.getTotalPages()-1)) {
                set(index, getForwardItemStack(), getForwardCommand(index));
            } else if(getBottomItemStack()!=null) {
                set(index, getBottomItemStack(), new GuiClickIndex(plugin, inventory, index, false));
            }
        }

        if(getBottomItemStack()!=null) {
            int indexBot = fullSize ? 45 : 19;
            int indexTop = fullSize ? 52 : 25;
            for(int i=indexBot; i<=indexTop; i++) {
                set(i, getBottomItemStack(), new GuiClickIndex(plugin, inventory, i, false));
            }
        }

    }

}
