package me.bubbles.bosspve.utility.gui.command;

import me.bubbles.bosspve.BossPVE;
import me.bubbles.bosspve.events.presets.GuiClickIndex;
import me.bubbles.bosspve.utility.UtilNumber;
import me.bubbles.bosspve.utility.gui.GUI;
import me.bubbles.bosspve.utility.pagifier.Gridifier;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.InventoryHolder;

public abstract class ClickGUI<T> extends GUI implements IClickGUI<T> {

    private final T[] array;
    private int indexCap;
    protected int page;
    private Gridifier<T> gridifier;

    public ClickGUI(BossPVE plugin, InventoryHolder holder, int rows, Class<T> clazz, T[] array, int page) {
        super(plugin, holder, rows);
        this.array = array;
        rows = (int) UtilNumber.clampBorder(6, 1, rows);
        //rows = fullSize ? 5 : 2;
        this.gridifier = new Gridifier<>(clazz, array, rows, 9);
        this.indexCap = (int) UtilNumber.clampBorder(54, 9, rows*9);
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

        if(getBackItemStack()!=null&& getBackClick(0)!=null) {
            //int index = fullSize ? 44 : 18;
            int index = indexCap-9;
            if(page>0) {
                set(index, getBackItemStack(), getBackClick(index));
            } else if(getBottomItemStack()!=null) {
                set(index, getBottomItemStack(), new GuiClickIndex(plugin, inventory, index, false));
            }
        }

        if(getForwardItemStack()!=null&& getForwardClick(0)!=null) {
            int index = indexCap-1;
            //int index = fullSize ? 53 : 26;
            if(page<(gridifier.getTotalPages()-1)) {
                set(index, getForwardItemStack(), getForwardClick(index));
            } else if(getBottomItemStack()!=null) {
                set(index, getBottomItemStack(), new GuiClickIndex(plugin, inventory, index, false));
            }
        }

        if(getBottomItemStack()!=null) {

            int indexBot = indexCap-8;
            int indexTop = indexCap-1;

            //int indexBot = fullSize ? 45 : 19;
            //int indexTop = fullSize ? 52 : 25;
            for(int i=indexBot; i<indexTop; i++) {
                set(i, getBottomItemStack(), new GuiClickIndex(plugin, inventory, i, false));
            }
        }

    }

}
