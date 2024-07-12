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

    public ClickGUI(InventoryHolder holder, int rows, Class<T> clazz, T[] array, int page) {
        super(holder, rows);
        this.array = array;
        rows = (int) UtilNumber.clampBorder(6, 2, rows);
        //rows = fullSize ? 5 : 2;
        this.gridifier = new Gridifier<>(clazz, array, (rows-1), 9);
        this.indexCap = (int) UtilNumber.clampBorder(53, 17, (rows*9)-1);
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

        for(int i=0; i<indexCap-8; i++) {

            int longIndex = i+((indexCap-8)*(page));

            if(array.length==longIndex) {
                break;
            }

            set(i, getItemStack(array[longIndex]), getGuiClick(array[longIndex], i));
        }

        if(getBackItemStack()!=null&&getBackClick(0)!=null) {
            //int index = fullSize ? 44 : 18;
            int index = indexCap-8;
            if(page>0) {
                set(index, getBackItemStack(), getBackClick(index));
            } else if(getBottomItemStack()!=null) {
                set(index, getBottomItemStack(), new GuiClickIndex(inventory, index, false));
            }
        }

        if(getForwardItemStack()!=null&&getForwardClick(0)!=null) {
            int index = indexCap;
            //int index = fullSize ? 53 : 26;
            if(page<(gridifier.getTotalPages()-1)) {
                set(index, getForwardItemStack(), getForwardClick(index));
            } else if(getBottomItemStack()!=null) {
                set(index, getBottomItemStack(), new GuiClickIndex(inventory, index, false));
            }
        }

        if(getBottomItemStack()!=null) {

            int indexBot = indexCap-7;
            int indexTop = indexCap;

            for(int i=indexBot; i<indexTop; i++) {
                set(i, getBottomItemStack(), new GuiClickIndex(inventory, i, false));
            }
        }

    }

}
