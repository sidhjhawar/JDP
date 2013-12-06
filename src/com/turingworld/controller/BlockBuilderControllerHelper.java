package com.turingworld.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

import com.turingworld.model.Block;
import com.turingworld.model.BlockBuilderModel;

public class BlockBuilderControllerHelper {

	DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
	Date date = new Date();

	private String jsonFilePath = "jsondata/Block-" + dateFormat.format(date) + ".json";
	private BlockBuilderModel model = new BlockBuilderModel();

	/*
	 * Function to convert the model into JSON objects
	 */

	public void convertToJSON(BlockBuilderModel blockBuilderModel) {
		JSONObject writeJSON = new JSONObject();
		JSONArray jsonArray = new JSONArray();
		for (Block block : blockBuilderModel.getBlockList()) {
			JSONObject jsonBlock = new JSONObject().element("Name", block.getName()).element("x", block.getX()).element("y", block.getY()).element("height", block.getHeight())
					.element("width", block.getWidth()).element("isstate", block.isState()).element("Transition Type", block.getTransitionType())
					.element("Block Label URL", block.getBlockLabelURL());
			System.out.println(jsonBlock.toString());
			jsonArray.add(jsonBlock);
		}

		writeJSON.put("Blocks", jsonArray);

		try {
			FileWriter Filewriter = new FileWriter(jsonFilePath);
			Filewriter.write(writeJSON.toString());
			Filewriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public BlockBuilderModel importJSON(File file) {

		BufferedReader br = null;
		String reader = "";

		try {
			String sCurrentLine;
			br = new BufferedReader(new FileReader(file));
			while ((sCurrentLine = br.readLine()) != null) {
				reader += sCurrentLine;
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null)
					br.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}

		JSONObject read = new JSONObject();
		read = (JSONObject) JSONSerializer.toJSON(reader);
		System.out.println(read.toString());
		JSONArray jsonArray = read.getJSONArray("Blocks");

		ArrayList<Block> arr = new ArrayList<Block>();

		for (Object key : jsonArray) {
			Block loadBlock = new Block();
			loadBlock.setName((String) ((JSONObject) key).get("Name"));
			loadBlock.setX((Integer) ((JSONObject) key).get("x"));
			loadBlock.setY((Integer) ((JSONObject) key).get("y"));
			loadBlock.setHeight((Integer) ((JSONObject) key).get("height"));
			loadBlock.setWidth((Integer) ((JSONObject) key).get("width"));
			loadBlock.setTransitionType((String) ((JSONObject) key).get("Transition Type"));
			loadBlock.setBlockLabelURL((String) ((JSONObject) key).get("Block Label URL"));
			loadBlock.setState((Boolean) ((JSONObject) key).get("isstate"));

			arr.add(loadBlock);
		}

		model.setBlockList(arr);
		return model;
	}
}
