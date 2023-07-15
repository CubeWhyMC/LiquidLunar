package org.cubewhy.lunarcn.gui;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.ResourceLocation;
import org.cubewhy.lunarcn.Client;
import org.cubewhy.lunarcn.utils.GitUtils;
import org.cubewhy.lunarcn.utils.RenderUtils;

import java.awt.*;

public class GuiCommitInfo extends GuiScreen {
    public static final ResourceLocation gitImage = new ResourceLocation("lunarcn/icons/git.png");

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawDefaultBackground();
        RenderUtils.drawImage(gitImage, 30, 30, 30, 30);
        String buildUser = GitUtils.gitInfo.getProperty("git.build.user.name");
        String version = GitUtils.gitInfo.getProperty("git.build.version");
        String commitId = GitUtils.gitInfo.getProperty("git.commit.id");
        String commitIdAbbrev = GitUtils.gitInfo.getProperty("git.commit.id.abbrev");
        String commitMessage = GitUtils.gitInfo.getProperty("git.commit.message.short");
        String branch = GitUtils.gitInfo.getProperty("git.branch");
        String repo = GitUtils.gitInfo.getProperty("git.remote.origin.url");
        this.drawString(fontRendererObj, "Git Info", 70 ,30, new Color(255, 255, 255).getRGB());
        this.drawString(fontRendererObj, Client.clientName + " built by: " + buildUser, 70, 30 + fontRendererObj.FONT_HEIGHT, new Color(255, 255, 255).getRGB());
        this.drawString(fontRendererObj, "Version (buildFile): " + version, 70, 30 + fontRendererObj.FONT_HEIGHT * 2 + 5, new Color(255, 255, 255).getRGB());
        this.drawString(fontRendererObj, "CommitId " + commitId + " (" + commitIdAbbrev + ")", 70, 30 + fontRendererObj.FONT_HEIGHT * 3 + 5, new Color(255, 255, 255).getRGB());
        this.drawString(fontRendererObj, "CommitMessage: " + commitMessage, 70, 30 + fontRendererObj.FONT_HEIGHT * 4 + 5, new Color(255, 255, 255).getRGB());
        this.drawString(fontRendererObj, "Branch: " + branch, 70, 30 + fontRendererObj.FONT_HEIGHT * 5 + 5, new Color(255, 255, 255).getRGB());
        this.drawString(fontRendererObj, "Remote origin: " + repo, 70, 30 + fontRendererObj.FONT_HEIGHT * 6 + 5, new Color(255, 255, 255).getRGB());
        super.drawScreen(mouseX, mouseY, partialTicks);
    }
}
