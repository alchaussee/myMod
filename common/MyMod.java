package myMod.common;


import java.util.Arrays;

import net.minecraft.block.Block;
import net.minecraft.block.BlockSand;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.Mod.PostInit;
import cpw.mods.fml.common.Mod.PreInit;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

@Mod(modid = "MyMod", name = "MyMod", version = "1.0")
@NetworkMod(clientSideRequired = true, serverSideRequired = false, channels = {"Structure"}, packetHandler = PacketHandler.class)

public class MyMod 
{
	public static Block testBaconFurnace;
	public static Item testItem;
	public static Item testIngot; 
	public static Item laputaAmulet;
	public static Item enderiteChunk;
	public static Item enderiteIngot;
	public static Item enderitePick;
	public static Item core;
	public static Item skewer;
	
	public static Item meatStew;
	public static Item kabob;
	public static Item baconAndEggs;
	
	public static Block condensedIngot;
	public static Block testBlock;
	public static Block testOre;
	public static Block structureBlock;
	public static Block structureBlockBase;
	public static Block enderiteOre;
	public static Block crockpotBlock;
	public static Block shellSandBlock;
	
	//public   static Block animBlock = new AnimBlock(507, 7, Material.rock).setHardness(2.0F).setStepSound(Block.soundStoneFootstep).setBlockName("animBlock").setCreativeTab(CreativeTabs.tabBlock);
	
	@Instance("MyMod")
	public static MyMod instance;

	@SidedProxy(clientSide="myMod.common.client.ClientProxy", serverSide = "myMod.common.CommonProxy")
	public static CommonProxy proxy;
	
	public static int testItemID;
	public static int testIngotID;
	public static int enderChunkID;
	public static int enderIngotID;
	public static int enderPickID;
	public static int coreID;
	public static int condensedIngotID;
	public static int testBlockID;
	public static int testOreID;
	public static int structureBlockID;
	public static int structureBlockBaseID;
	public static int enderiteOreID;
	public static int laputaAmuletID;
	public static int crockpotBlockID;
	public static int meatStewID;
	public static int kabobID;
	public static int skewerID;
	public static int shellSandID;
	public static int baconAndEggsID;
	
	@PreInit
	public void preInit(FMLPreInitializationEvent event)
	{
		Configuration config = new Configuration(event.getSuggestedConfigurationFile());
		config.load();
		testItemID = config.getItem("TestItem", 5000).getInt();
		testIngotID = config.getItem("TestIngot", 5001).getInt();
		laputaAmuletID = config.getItem("LaputaAmulet", 5002).getInt();
		enderChunkID = config.getItem("EnderChunk", 5003).getInt();
		enderIngotID = config.getItem("EnderIngot", 5004).getInt();
		enderPickID = config.getItem("EnderPick", 5005).getInt();
		coreID = config.getItem("Core", 5006).getInt();
		
		meatStewID = config.getItem("MeatStew", 5007).getInt();
		kabobID = config.getItem("Kabob", 5008).getInt();
		skewerID = config.getItem("Skewer", 5009).getInt();
		baconAndEggsID = config.getItem("Bacon&Eggs", 5010).getInt();
		
		condensedIngotID = config.getBlock("CondensedIngot", 505).getInt();
		testBlockID = config.getBlock("TestBlock", 500).getInt();
		testOreID = config.getBlock("TestOre", 501).getInt();
		structureBlockID = config.getBlock("StructureBlock", 503).getInt();
		structureBlockBaseID = config.getBlock("StructureBlockBase", 504).getInt();
		enderiteOreID = config.getBlock("EnderiteOre", 506).getInt();
		crockpotBlockID = config.getBlock("CrockpotBlock", 507).getInt();
		shellSandID = config.getBlock("ShellSand", 508).getInt();
		config.save();
	}
	
	@Init
	public void load(FMLInitializationEvent event)
	{
		testItem = new TestItem(testItemID).setMaxStackSize(1).setIconIndex(0).setItemName("testItem").setCreativeTab(CreativeTabs.tabMisc);
		testIngot = new TestItem(testIngotID).setMaxStackSize(64).setIconIndex(1).setItemName("testIngot").setCreativeTab(CreativeTabs.tabMisc);
		laputaAmulet = new LaputaAmuletItem(laputaAmuletID).setMaxStackSize(1).setIconIndex(2).setItemName("laputaAmulet").setCreativeTab(CreativeTabs.tabMisc);
		enderiteChunk = new TestItem(enderChunkID).setMaxStackSize(64).setIconIndex(3).setItemName("enderiteChunk").setCreativeTab(CreativeTabs.tabMisc);
		enderiteIngot = new TestItem(enderIngotID).setMaxStackSize(64).setIconIndex(5).setItemName("enderiteIngot").setCreativeTab(CreativeTabs.tabMisc);
		enderitePick = new EnderitePick(enderPickID, EnumToolMaterial.EMERALD).setMaxStackSize(1).setIconIndex(4).setItemName("enderitePick").setCreativeTab(CreativeTabs.tabTools);
		core = new CoreItem(coreID).setMaxStackSize(1).setIconIndex(6).setItemName("core").setCreativeTab(CreativeTabs.tabMisc);
		skewer = new SkewerItem(skewerID).setMaxStackSize(64).setIconIndex(9).setCreativeTab(CreativeTabs.tabCombat).setItemName("skewer");
		
		meatStew = new ItemFood(meatStewID, 20, 2.0F, false).setIconCoord(7, 0).setItemName("meatStew").setTextureFile(proxy.ITEMS_PNG).setMaxStackSize(16);
		kabob = new kabobFood(kabobID, 4, 1.0F, false).setIconCoord(8, 0).setItemName("kabob").setTextureFile(proxy.ITEMS_PNG).setMaxStackSize(64);
		baconAndEggs = new ItemFood(baconAndEggsID, 14, 1.5F, false).setIconCoord(10, 0).setItemName("baconAndEggs").setTextureFile(proxy.ITEMS_PNG).setMaxStackSize(64);

		condensedIngot = new TestBlock(condensedIngotID, 5, Material.glass).setHardness(1.0F).setStepSound(Block.soundGlassFootstep).setBlockName("condensedIngot").setCreativeTab(CreativeTabs.tabBlock);
		testBlock = new TestBlock(testBlockID, 0 , Material.rock).setHardness(1.5F).setStepSound(Block.soundStoneFootstep).setBlockName("testBlock").setCreativeTab(CreativeTabs.tabBlock);
		testOre = new TestOre(testOreID, 1, Material.rock).setHardness(3.0F).setStepSound(Block.soundStoneFootstep).setBlockName("testOre").setCreativeTab(CreativeTabs.tabBlock);
		structureBlock = new StructureBlock(structureBlockID, 3, false).setHardness(2.0F).setStepSound(Block.soundStoneFootstep).setBlockName("structureBlock").setCreativeTab(CreativeTabs.tabBlock);
		structureBlockBase = new StructureBlock(structureBlockBaseID, 3, true).setHardness(2.0F).setBlockName("structureBlock");
		enderiteOre = new EnderiteOre(enderiteOreID, 6).setHardness(3.0F).setStepSound(Block.soundStoneFootstep).setBlockName("enderiteOre").setCreativeTab(CreativeTabs.tabBlock);
		crockpotBlock = new CrockpotBlock(crockpotBlockID, 4, false).setHardness(2.0F).setStepSound(Block.soundStoneFootstep).setBlockName("crockpotBlock").setCreativeTab(CreativeTabs.tabBlock);
		shellSandBlock = new BlockSand(shellSandID, 7).setHardness(0.5F).setStepSound(Block.soundSandFootstep).setBlockName("shellSand").setTextureFile(proxy.BLOCK_PNG);
		
		ItemStack testIngotStack = new ItemStack(MyMod.testIngot);
		ItemStack structureBlockStack = new ItemStack(MyMod.structureBlock);
		ItemStack condensedIngotStack = new ItemStack(MyMod.condensedIngot);
		ItemStack laputaAmuletStack = new ItemStack(MyMod.laputaAmulet);
		ItemStack enderiteIngotStack = new ItemStack(MyMod.enderiteIngot);
		
		GameRegistry.addRecipe(condensedIngotStack,"xxx","xxx","xxx", 'x', testIngotStack);
		GameRegistry.addRecipe(new ItemStack(MyMod.core), "yxy", "xzx", "yxy", 'x', testIngotStack, 'y', new ItemStack(MyMod.enderiteChunk), 'z', new ItemStack(Item.enderPearl));
		GameRegistry.addRecipe(structureBlockStack,"zxz","xyx","zxz", 'x', new ItemStack(Item.ingotIron), 'y', new ItemStack(MyMod.core), 'z', new ItemStack(Block.stone));
		GameRegistry.addRecipe(laputaAmuletStack, "xxx","xyx","xxx", 'x', new ItemStack(Item.diamond), 'y', condensedIngotStack);
		GameRegistry.addSmelting(MyMod.enderiteChunk.itemID, enderiteIngotStack, 0.0f);
		GameRegistry.addRecipe(new ItemStack(MyMod.enderitePick), "xxx"," y "," y ", 'x', enderiteIngotStack, 'y',new ItemStack(Item.stick));
		
		LanguageRegistry.addName(testItem, "SCOOP!");
		LanguageRegistry.addName(testIngot, "Laputa Crystal");
		LanguageRegistry.addName(laputaAmulet, "Laputa Amulet");
		LanguageRegistry.addName(enderiteChunk, "Enderite Chunk");
		LanguageRegistry.addName(enderiteIngot, "Enderite Ingot");
		LanguageRegistry.addName(enderitePick, "Enderite Pickaxe");
		LanguageRegistry.addName(core, "\u00a79Laputa Core");
		LanguageRegistry.addName(meatStew, "Meat Stew");
		LanguageRegistry.addName(kabob, "Kabob");
		LanguageRegistry.addName(skewer, "Skewer");
		LanguageRegistry.addName(baconAndEggs, "Bacon & Eggs");
		
		LanguageRegistry.addName(condensedIngot, "Laputa Block");
		GameRegistry.registerBlock(condensedIngot, "condensedIngot");
		
		LanguageRegistry.addName(testBlock, "Bacon Stone");
		MinecraftForge.setBlockHarvestLevel(testBlock, "pickaxe", 1);
		GameRegistry.registerBlock(testBlock, "testBlock");
		
		LanguageRegistry.addName(testOre, "Laputa Ore");
		MinecraftForge.setBlockHarvestLevel(testOre, "pickaxe", 1);
		GameRegistry.registerBlock(testOre, "testOre");
		GameRegistry.registerWorldGenerator(new TestWorldGenerator());
		
		LanguageRegistry.addName(structureBlock, "Island Constructor");
		GameRegistry.registerBlock(structureBlock, "structureBlock");
		GameRegistry.registerTileEntity(TileEntityStructureBlock.class,"tileEntityStructureBlock");
		
		GameRegistry.registerBlock(structureBlockBase, "structureBlockBase");
		
		LanguageRegistry.addName(crockpotBlock, "Crock Pot");
		MinecraftForge.setBlockHarvestLevel(crockpotBlock, "pickaxe", 1);
		GameRegistry.registerBlock(crockpotBlock, "crockpotBlock");
		GameRegistry.registerTileEntity(TileEntityCrockpotBlock.class,"tileEntityCrockpotBlock");
		
		RecipiesCrockpot.smelting().addAllMutations(new int[] {new ItemStack(Item.beefRaw).itemID, new ItemStack(Item.porkRaw).itemID, new ItemStack(Item.chickenRaw).itemID, new ItemStack(Item.beefRaw).itemID}, new ItemStack(MyMod.meatStew));
		RecipiesCrockpot.smelting().addSmelting(new int[] {new ItemStack(Item.beefRaw).itemID, new ItemStack(Item.stick).itemID, 0, 0}, new ItemStack(MyMod.kabob), 0.0F);
		RecipiesCrockpot.smelting().addSmelting(new int[] {new ItemStack(Item.chickenRaw).itemID, new ItemStack(Item.stick).itemID, 0, 0}, new ItemStack(MyMod.kabob), 0.0F);
		RecipiesCrockpot.smelting().addSmelting(new int[] {new ItemStack(Item.porkRaw).itemID, new ItemStack(Item.stick).itemID, 0, 0}, new ItemStack(MyMod.kabob), 0.0F);
		RecipiesCrockpot.smelting().addSmelting(new int[] {new ItemStack(Item.fishRaw).itemID, new ItemStack(Item.stick).itemID, 0, 0}, new ItemStack(MyMod.kabob), 0.0F);
		RecipiesCrockpot.smelting().addSmelting(new int[] {new ItemStack(Item.egg).itemID, new ItemStack(Item.porkRaw).itemID, new ItemStack(Item.porkRaw).itemID, 0}, new ItemStack(MyMod.baconAndEggs), 0.0F);
		
		int id = EntityRegistry.findGlobalUniqueEntityId();
		EntityRegistry.registerGlobalEntityID(EntityTestOre.class, "entityTestOre", id);
		EntityRegistry.registerModEntity(EntityTestOre.class, "entityTestOre", id, instance, 160, 20, true);
		
		LanguageRegistry.addName(enderiteOre, "Enderite Ore");
		MinecraftForge.setBlockHarvestLevel(enderiteOre, "pickaxe", 2);
		GameRegistry.registerBlock(enderiteOre, "enderiteOre");
		GameRegistry.registerWorldGenerator(new EnderiteWorldGenerator());
		
		GameRegistry.registerWorldGenerator(new IslandWorldGenerator());
		
		int id1 = EntityRegistry.findGlobalUniqueEntityId();
		EntityRegistry.registerGlobalEntityID(EntitySkewer.class, "entitySkewer", id1);
		EntityRegistry.registerModEntity(EntitySkewer.class, "entitySkewer", id1, instance, 160, 20, true);
		
		LanguageRegistry.addName(shellSandBlock, "Shell Sand");
		MinecraftForge.setBlockHarvestLevel(shellSandBlock, "shovel", 0);
		GameRegistry.registerBlock(shellSandBlock, "shellSand");
		GameRegistry.registerWorldGenerator(new ShellSandWorldGenerator());
		
		//LanguageRegistry.addName(animBlock, "Animated Block");
		//GameRegistry.registerBlock(animBlock, "animBlock");
		
		NetworkRegistry.instance().registerGuiHandler(this, new GuiHandler());
		
		proxy.registerRenderers();
		//proxy.registerFX();
	}
	
	@PostInit
	public void postInit(FMLPostInitializationEvent event)
	{
		
	}
}
