package myMod.common;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class RecipiesCrockpot 
{
	private static final RecipiesCrockpot crockpotBase = new RecipiesCrockpot();

	/** The list of smelting results. */
	private Map crockpotList = new HashMap();
	private Map crockpotExperience = new HashMap();
	private HashMap<List<Integer>, ItemStack> metaSmeltingList = new HashMap<List<Integer>, ItemStack>();
	private HashMap<List<Integer>, Float> metaExperience = new HashMap<List<Integer>, Float>();

	private ArrayList<int[]> recipes = new ArrayList<int[]>();
	private ArrayList<ItemStack> output = new ArrayList<ItemStack>();
	
	/**
	* Used to call methods addSmelting and getSmeltingResult.
	*/
	public static final RecipiesCrockpot smelting()
	{
		return crockpotBase;
	}

	private RecipiesCrockpot()
	{

	}

	/**
	* Adds a smelting recipe.
	*/
	public void addSmelting(int[] temp, ItemStack item, float experience)
	{
		if(temp.length!=4)
			return;
		recipes.add(temp);
		output.add(item);
		//this.crockpotExperience.put(Integer.valueOf(itemStack.itemID), Float.valueOf(experience));
	}
	
	private ItemStack getSmeltingResult(int id, int id2, int id3, int id4)
	{
		for(int i=0; i<recipes.size(); i++)
		{
			int[] rec = recipes.get(i);
			ArrayList<Integer> rec2 = new ArrayList<Integer>();
			for(int j:rec)
				rec2.add(j);

			rec2.remove((Integer)id);
			rec2.remove((Integer)id2);
			rec2.remove((Integer)id3);
			rec2.remove((Integer)id4);
			
			if(rec2.size()==0)
				return output.get(i);
		}
		return (ItemStack)null;
	}
	
	private int checkMatch(int[] id, ArrayList<Integer> rec)
	{
		for(int i=0; i<id.length; i++)
		{
			for(int j=0; j<rec.size(); j++)
			{
				if(id[i] == rec.get(j))
					return id[i];
			}
		}
		return -1;
	}

	public void addAllMutations(int[] temp, ItemStack output)
	{
		for(int i=0; i<temp.length; i++)
		{
			for(int j = i; j<temp.length; j++)
			{
				for(int k = j; k<temp.length;k++)
				{
					for(int l = k; l<temp.length;l++)
					{
						this.addSmelting(new int[] {temp[i], temp[j], temp[k], temp[l]}, output, 0.0F);
					}
				}
			}
		}
	}


	void permute(List<Integer> arr, int k, ItemStack output1)
	{
        for(int i = k; i < arr.size(); i++)
        {
            Collections.swap(arr, i, k);
            permute(arr, k+1, output1);
            Collections.swap(arr, k, i);
        }
        if (k == arr.size() -1)
        {
        	int[] temp = new int[arr.size()];
            for(int i=0;i<arr.size();i++)
            {
            	temp[i] = arr.get(i);
            	System.out.print(temp[i]+", ");
            }
            System.out.println();
            addSmelting(temp, output1, 0.0F);
        }
    }
    
	/*
	public ItemStack getSmeltingResult(int id)
	{
		return (ItemStack)crockpotList.get(Integer.valueOf(id));
	}
	*/
	
	public ItemStack getSmeltingResult(ItemStack item, ItemStack item2, ItemStack item3, ItemStack item4)
	{
		int itemID;
		int item2ID;
		int item3ID;
		int item4ID;
		
		if(item == null)
			itemID = 0;
		else
			itemID = item.itemID;
		
		if(item2 == null)
			item2ID = 0;
		else
			item2ID = item2.itemID;
		
		if(item3 == null)
			item3ID = 0;
		else
			item3ID = item3.itemID;
		
		if(item4 == null)
			item4ID = 0;
		else
			item4ID = item4.itemID;
		
		ItemStack ret = getSmeltingResult(itemID, item2ID, item3ID, item4ID);
        if (ret != null)
        {
        	return ret;
        }
        return (ItemStack)null;
	}


	public Map getSmeltingList()
	{
		return crockpotList;
	}
	
	public float getExperience(int par1)
	{
		//return this.crockpotExperience.containsKey(Integer.valueOf(par1)) ? ((Float)this.crockpotExperience.get(Integer.valueOf(par1))).floatValue() : 0.0F;
		return 0.0F;
	}
	
	public float getExperience(ItemStack item)
	{
		/*
         if (item == null || item.getItem() == null)
         {
        	 return 0;
         }
         float ret = item.getItem().getSmeltingExperience(item);
         if (ret < 0 && metaExperience.containsKey(Arrays.asList(item.itemID, item.getItemDamage())))
         {
             ret = metaExperience.get(Arrays.asList(item.itemID, item.getItemDamage()));
         }
         if (ret < 0 && crockpotExperience.containsKey(item.itemID))
         {
             ret = ((Float)crockpotExperience.get(item.itemID)).floatValue();
         }
         return (ret < 0 ? 0 : ret);
         */
		return 0.0F;
	}
}
