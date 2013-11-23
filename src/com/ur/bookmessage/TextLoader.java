package com.ur.bookmessage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Logger;

import com.ur.onigokko.Main;

public class TextLoader {
	private Main main;
	private File file;
	private File folder;
	private String path;
	private FileReader Freader;
	private BufferedReader Breader;
	private ArrayList<String> result;
	private String TFileName = "messages.txt";
	public TextLoader(Main main)
	{
		this.main = main;
		ready();
	}
	private void ready()
	{
		try{
			path = main.getDataFolder().getAbsolutePath() + TFileName;
			file = new File(path);
			if(!file.exists())
			{
				file.createNewFile();
			}
			Freader = new FileReader(file);
			Breader = new BufferedReader(Freader);
			String tmp = null;
			result = new ArrayList<String>();
			while ((tmp = Breader.readLine()) != null)
			{
				result.add(tmp);
			}
			Breader.close();
		}catch(IOException e){
			
		}

	}
	public ArrayList<String> getALLText()
	{
		return result;
	}
	public String getTextOfLine(int i)
	{
		if(i > result.size())
		{
			return null;
		}
		return result.get(i - 1);
	}

}
