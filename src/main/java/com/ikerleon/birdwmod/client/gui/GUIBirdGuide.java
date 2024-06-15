package com.ikerleon.birdwmod.client.gui;

import com.ikerleon.birdwmod.Main;
import com.ikerleon.birdwmod.entity.BirdEntity;
import com.ikerleon.birdwmod.entity.BirdSettings;
import com.ikerleon.birdwmod.entity.InitEntities;
import com.ikerleon.birdwmod.items.InitItems;
import com.mojang.blaze3d.systems.RenderSystem;
import edu.umd.cs.findbugs.annotations.Nullable;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.PageTurnWidget;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.NarratorManager;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.CowEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.text.*;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import org.joml.Quaternionf;
import org.joml.Vector3f;
import org.lwjgl.opengl.GL11;

public class GUIBirdGuide extends Screen {
    private final int bookImageHeight = 180;
    private final int bookImageWidth = 292;

    private int currPage = 0;
    private static final int bookTotalPages = 17;

    private ButtonWidget buttonDone;
    private ButtonWidget buttonNextPage;
    private ButtonWidget buttonPreviousPage;
    private static final Identifier cover= Identifier.of("birdwmod" + ":textures/gui/birdguide/cover.png");
    private static final Identifier page= Identifier.of("birdwmod" + ":textures/gui/birdguide/page.png");

    private static final String Waterfowl = Formatting.GRAY + "Waterfowl";
    private static final String Galliformes = Formatting.GRAY + "Galliformes";
    private static final String Waders = Formatting.GRAY + "Waders";
    private static final String GullsBoobies = Formatting.GRAY + "Gulls & Boobies";
    private static final String Auks = Formatting.GRAY + "Auks";
    private static final String Owls = Formatting.GRAY + "Owls";
    private static final String Nightjars = Formatting.GRAY + "Nightjars";
    private static final String Passerines = Formatting.GRAY + "Passerines";
    private static final String Herons = Formatting.GRAY + "Herons";
    private static final String Coraciiformes = Formatting.GRAY + "Coraciiformes";
    private static final String Opisthocomiformes = Formatting.GRAY + "Opisthocomiformes";

    private static final String CharacteristicsTitle = Formatting.BOLD + "Characteristics";
    private static final String BiomesTitle = Formatting.BOLD + "Vanilla Biomes";

    private static final String page9Title = Formatting.BOLD + "Red-necked nightjar";
    private static final String page9Subtitle = Formatting.ITALIC + "(Caprimulgus ruficollis)";
    private static final String page9Text = "It's the largest of the nightjars occurring in Europe. It breeds in Iberia and north Africa, and winters in tropical west Africa. Open sandy heaths with trees or bushes are the haunts of this crepuscular bird. In flight it presents a characteristic silhouette with silent flight and low altitude.";

    private static final String page10Title = Formatting.BOLD + "Northern Mockingbird";
    private static final String page10Subtitle = Formatting.ITALIC + "(Mimus polyglottos)";
    private static final String page10Text = "It's are best known for the habit of mimicking the songs of other birds and the sounds of insects and amphibians. This bird is mainly a permanent resident, but northern birds may move south during harsh weather. Northern mockingbirds are omnivore. It's often found in open areas and forest edges.";

    private static final String page11Title = Formatting.BOLD + "Eastern bluebird";
    private static final String page11Subtitle = Formatting.ITALIC + "(Sialia sialis)";
    private static final String page11Text = "It's a small thrush found in open woodlands, farmlands, and orchards of North America. The Eastern bluebird is the state bird of New York. About two-thirds of the diet of an adult consists of insects and other invertebrates. Eastern bluebirds tend to live in open country around trees.";

    private static final String page12Title = Formatting.BOLD + "Red-flanked bluetail";
    private static final String page12Subtitle = Formatting.ITALIC + "(Tarsiger cyanurus)";
    private static final String page12Text = "It's a small passerine bird that lives in the coniferous forests of Eurasia. It breeds in upper-middle and marginally in upper continental latitudes, exclusively boreal and mountain. Its diet is based on insects, also fruits and seeds outside breeding season.";

    private static final String page14Title1 = Formatting.BOLD + "King-of-Saxony";
    private static final String page14Title2 = Formatting.BOLD + "bird of paradise";
    private static final String page14Subtitle = Formatting.ITALIC + "(Pteridophora alberti)";
    private static final String page14Text = "It's a bird of paradise endemic to montane forest in New Guinea. The most iconic characteristic of this bird are the two remarkably long (up to 50 cm) brow-plumes, which are so bizarre that when the first specimen was brought to Europe, it was thought to be a fake.";

    private static final String page15Title1 = Formatting.BOLD + "Turquoise-browed";
    private static final String page15Title2 = Formatting.BOLD + "motmot";
    private static final String page15Subtitle = Formatting.ITALIC + "(Eumomota superciliosa)";
    private static final String page15Text = "It's a colorful bird that lives all across Central America, from south-east Mexico (mostly the Yucatán Peninsula), to Costa Rica. It lives in habitats such as forest edge or gallery forest. it often perches from wires or posts where it scans for prey, such as insects and small reptiles.";

    private static final String page16Title = Formatting.BOLD + "Hoatzin";
    private static final String page16Subtitle = Formatting.ITALIC + "(Opisthocomus hoazin)";
    private static final String page16Text = "It's a tropical, dinosaur-type bird that can be found in swamps, riparian forests, and mangroves of the Amazon and the Orinoco basins in South America. It is notable for having chicks that have claws on two of their wing digits. The hoatzin is a folivore, in other words it eats the leaves";

    public GUIBirdGuide() {
        super(NarratorManager.EMPTY);
    }

    @Override
    protected void init() {
        if(client == null) return;
        int offLeft = (int)((this.width - bookImageWidth) / 2.0F);
        int offTop = (int)((this.height - bookImageHeight) / 2.0F);

//        this.client.keyboard.setRepeatEvents(true);
        buttonDone = ButtonWidget.builder(ScreenTexts.DONE, (a) -> {
            this.client.setScreen(null);
        }).dimensions(offLeft+(bookImageWidth/2)-50, offTop+bookImageHeight+15, 100, 20).build();

        this.addDrawableChild(buttonDone);
        this.addDrawableChild(buttonNextPage = ButtonWidget.builder(Text.literal("->"), (a) -> {
            if (currPage < bookTotalPages - 1)
            {
                ++currPage;
                buttonNextPage.visible = (currPage < bookTotalPages - 1);
                buttonPreviousPage.visible = currPage > 0;
            }
        }).dimensions(offLeft+bookImageWidth+15, offTop, 50, 20).build());
        this.addDrawableChild(buttonPreviousPage = ButtonWidget.builder(Text.literal("<-"), (a) -> {
            if (currPage > 0)
            {
                --currPage;
                buttonPreviousPage.visible = currPage > 0;
                buttonNextPage.visible = (currPage < bookTotalPages - 1);
            }
        }).dimensions(offLeft-65, offTop, 50, 20).build());
        buttonPreviousPage.visible = false;
    }

    public static MutableText getTranslatedText(@Nullable Formatting format, BirdEntity bird, String section){
        MutableText text = Text.translatable("gui." + Main.ModID + "." + bird.getPath() + "_" + section);
        if(format!=null) {
            return text.formatted(format);
        }
        return text;
    }

    public static MutableText getTranslatedText(BirdEntity bird, String section){
        return getTranslatedText(null, bird, section);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        int offLeft = (int)((this.width - 292) / 2.0F);
        int offTop = (int)((this.height - 225) / 2.0F);
        int mousePosX = mouseX;
        int mousePosY = mouseY;

        if(currPage==0) {
            RenderSystem.setShader(GameRenderer::getPositionTexProgram);
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
            RenderSystem.setShaderTexture(0, cover);
            context.drawTexture(cover, offLeft, offTop, 0, 0, bookImageWidth, bookImageHeight, bookImageWidth, bookImageHeight);
            super.render(context, mouseX, mouseY, delta);
            return;
        }

        RenderSystem.setShader(GameRenderer::getPositionTexProgram);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, page);
        context.drawTexture(page, offLeft, offTop, 0, 0, bookImageWidth ,bookImageHeight ,bookImageWidth ,bookImageHeight);

        BirdEntity.Settings birdSettings = BirdSettings.bookBirds.get(currPage - 1);
        BirdEntity entity = new BirdEntity(InitEntities.GUI_BIRD_ENTITY, MinecraftClient.getInstance().world, birdSettings);
        context.drawText(textRenderer, getTranslatedText(Formatting.BOLD, entity, "title"), offLeft+(bookImageWidth/4),offTop + 15,0, false);
        context.drawText(textRenderer, getTranslatedText(Formatting.ITALIC, entity, "subtitle"), offLeft+(bookImageWidth/4),offTop + 25,0, false);
        context.drawText(textRenderer, getTranslatedText(Formatting.ITALIC, entity, "text"), offLeft + 13, 40 + offTop,0, false);
        context.drawText(textRenderer, CharacteristicsTitle, offLeft+(bookImageWidth/4)+(bookImageWidth/2),offTop + 15,0, false);
        context.drawText(textRenderer, "", offLeft+(bookImageWidth/4)+(bookImageWidth/2), offTop + 25,0, false);
        context.drawText(textRenderer, Formatting.ITALIC + "Male", offLeft + 13, 40 + offTop,0, false);
        context.drawText(textRenderer, BiomesTitle, offLeft+(bookImageWidth/4)+(bookImageWidth/2), 125 + offTop,0, false);
        context.drawText(textRenderer, birdSettings.spawnBiomesAsString(),  offLeft + 160, 140 + offTop,0, false);
        entity.setGender(0);
        entity.setVariant(0);
        entity.setOnGround(true);

//        this.itemRenderer.renderGuiItemIcon(new ItemStack(entity.getFeatherItem(), 1), offLeft + 175, 95 + offTop);

        Vector3f vector3f = new Vector3f(0.0f, entity.getHeight() / 2.0f, 0.0f);
        float h = (float)(mouseX + mouseY) / 2.0f;
        float j = (float)Math.atan((h - mouseY) / 40.0f);
        Quaternionf quaternionf = new Quaternionf().rotateZ((float)Math.PI);
        Quaternionf quaternionf2 = new Quaternionf().rotateX(j * 20.0f * ((float)Math.PI / 180));
        quaternionf.mul(quaternionf2);
        InventoryScreen.drawEntity(context, offLeft+((float) bookImageWidth /4)+((float) bookImageWidth /2), offTop + 75, (int)(entity.getScaleFactor() * 100), vector3f, quaternionf, quaternionf2, entity);

        super.render(context, mouseX, mouseY, delta);
    }

    @Override
    public boolean shouldPause() {
        return false;
    }
//    @Override
//    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
//        int offLeft = (int)((this.width - 292) / 2.0F);
//        int offTop = (int)((this.height - 225) / 2.0F);
//        int mousePosX = mouseX;
//        int mousePosY = mouseY;
//
//        if(currPage==0) {
//            RenderSystem.setShader(GameRenderer::getPositionTexProgram);
//            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
//            RenderSystem.setShaderTexture(0, cover);
//            drawTexture(matrices, offLeft, offTop, 0, 0, bookImageWidth, bookImageHeight, bookImageWidth, bookImageHeight);
//            super.render(matrices, mouseX, mouseY, delta);
//            return;
//        }
//        RenderSystem.setShader(GameRenderer::getPositionTexShader);
//        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
//        RenderSystem.setShaderTexture(0, page);
//        drawTexture(matrices, offLeft, offTop, 0, 0, bookImageWidth ,bookImageHeight ,bookImageWidth ,bookImageHeight);
//
//        BirdEntity.Settings birdSettings = BirdSettings.bookBirds.get(currPage - 1);
//        BirdEntity entity = new BirdEntity(InitEntities.GUI_BIRD_ENTITY, MinecraftClient.getInstance().world, birdSettings);
//        addDrawable(matrices, textRenderer, getTranslatedText(Formatting.BOLD, entity, "title"), offLeft+(bookImageWidth/4),offTop + 15, 0);
//        drawCenteredText(matrices, textRenderer, getTranslatedText(Formatting.ITALIC, entity, "subtitle"), offLeft+(bookImageWidth/4),offTop + 25,0);
//        this.textRenderer.drawTrimmed(StringVisitable.plain(getTranslatedText(entity, "text").getString()), offLeft + 13, 40 + offTop, 126, 0);
//        drawCenteredText(matrices, textRenderer, CharacteristicsTitle, offLeft+(bookImageWidth/4)+(bookImageWidth/2),offTop + 15,0);
//        drawCenteredText(matrices, textRenderer, "", offLeft+(bookImageWidth/4)+(bookImageWidth/2),offTop + 25,0);
//        drawCenteredText(matrices, textRenderer, Formatting.ITALIC + "Male", offLeft+(bookImageWidth/4)+(bookImageWidth/2),offTop + 80,0);
//        drawCenteredText(matrices, textRenderer, BiomesTitle, offLeft+(bookImageWidth/4)+(bookImageWidth/2),offTop + 125,0);
//        this.textRenderer.drawTrimmed(StringVisitable.plain(birdSettings.spawnBiomesAsString()), offLeft + 160, 140 + offTop,110,  0);
//
//        entity.setGender(0);
//        entity.setVariant(0);
//        entity.setOnGround(true);
//
//        this.itemRenderer.renderGuiItemIcon(new ItemStack(entity.getFeatherItem(), 1), offLeft + 175, 95 + offTop);
//
//        InventoryScreen.drawEntity(offLeft+(bookImageWidth/4)+(bookImageWidth/2), offTop + 75, (int)(entity.getScaleFactor() * 100), (float)(offLeft) - mousePosX, (float)(offTop + 75 - 50) - mousePosY, entity);
//
//        super.render(matrices, mouseX, mouseY, delta);
//    }
//
}
