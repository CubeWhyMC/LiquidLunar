package org.cubewhy.lunarcn.gui.mainmenu.lunar.ui;

import org.cubewhy.lunarcn.account.OfflineAccount;
import org.cubewhy.lunarcn.gui.mainmenu.lunar.ui.buttons.AccountButton;
import org.cubewhy.lunarcn.utils.RenderUtils;

import static org.cubewhy.lunarcn.utils.MinecraftInstance.mc;

public class DropDownList {
    protected AccountButton[] items;
    protected int x, y;
    protected int width, height;

    public AccountButton currentItem;

    private AccountButton currentHeld;

    public int hoverFade = 0;

    protected boolean hovered = false;

    public DropDownList(AccountButton[] items, int x, int y) {
        this.items = items;
        this.x = x;
        this.y = y;

        this.width = 132;
        this.height = 11;

        this.currentItem = items[0];
    }

    public DropDownList(int x, int y) {
        this(new AccountButton[]{new AccountButton(new OfflineAccount(mc.session.getUsername()))}, x, y);
    }

    public void drawList(int mouseX, int mouseY) {
//        boolean hovered = mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.width && mouseY < this.y + this.height;
        this.width = currentItem.width;

        hovered = RenderUtils.isHovering(mouseX, mouseY, this.x, this.y, this.x + this.width, this.y + this.height);

        if (hovered) {
            if (hoverFade < 40) hoverFade += 10;
        } else {
            if (hoverFade > 0) hoverFade -= 10;
        }

        if (hovered) {
            // TODO 展开列表
            for (int i = 0; i < items.length; i++) {
                AccountButton item = items[i];
                item.x = this.x;
                if (item.y < this.y + i * item.height + 5 && i != 0) {
                    item.y++;
                }
                item.drawButton(mouseX, mouseY);
                if (item.hoverFade > 0) {
                    this.currentHeld = item;
                }
            }
        } else {
            // 重置下拉列表
            for (int i = 0; i < items.length; i++) {
                AccountButton item = items[i];
                item.x = this.x;
                item.y = this.y;
            }

            currentItem.x = this.x;
            currentItem.y = this.y;
            currentItem.drawButton(mouseX, mouseY);
        }

        this.height = 0;

        for (int i = 0; i < items.length; i++) {
            AccountButton item = items[i];
            if (hovered) {
                this.height += item.height + 5;
            } else {
                this.height = currentItem.height;
                break;
            }
        }
    }

    public AccountButton getCurrentHeld() {
        return currentHeld;
    }

    public void reset() {
        for (int i = 0; i < items.length; i++) {
            AccountButton item = items[i];
            item.x = this.x;
            item.y = this.y;
        }
    }
}
