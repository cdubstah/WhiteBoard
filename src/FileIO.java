import java.awt.image.RenderedImage;
import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.*;
import java.util.*;
import javax.imageio.ImageIO;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.WritableImage;

public class FileIO {
	
	private WhiteBoardView view;
	
	public FileIO(WhiteBoardView view){
		this.view = view;
	}

	/*public void save(File f, ArrayList<DShape> list){
		//FileOutputStream fop;
		
		try{
			ObjectOutputStream oop = new ObjectOutputStream(new FileOutputStream(f));
			
			System.out.println("Writing objects to file");
			ArrayList<DShapeModel> model = new ArrayList<>();
			
			for(DShape ds : list){
				model.add(ds.getShapeModel());
			}
			
			for(DShapeModel dm : model){
				oop.writeDouble(dm.getX());
				oop.writeDouble(dm.getY());
				oop.writeDouble(dm.getWidth());
				oop.writeDouble(dm.getHeight());
				oop.writeDouble(dm.getRed());
				oop.writeDouble(dm.getGreen());
				oop.writeDouble(dm.getBlue());
				oop.writeDouble(dm.getOpacity());
			}
		} catch(IOException e){
			System.out.println("IO EXCEPTION: " + e.getMessage());
			System.out.println(e.getStackTrace());
		}
		try{
			//FileOutputStream fop = new FileOutputStream(f);
			FileOutputStream fop = new FileOutputStream("objects.txt");		
			ObjectOutputStream oop = new ObjectOutputStream(fop);
			
			System.out.println("Writing Objects to file");
			for(DShape ds : list){
				System.out.println("Writing" + ds);
				oop.writeObject(ds);
			}
			
			oop.close();
			//fop.close();
			System.out.println("Done");
			
		} catch(IOException e){
			System.out.println("IO EXCEPTION: " + e.getMessage());
			System.out.println(e.getStackTrace());
		} 
	}*/
	
	/*//Opens file and reads from it
	public void open(File f){
		try{
			ObjectInputStream oip = new ObjectInputStream(new FileInputStream(f));
			ArrayList<DShape> list = new ArrayList<>();
			
			while(oip.readDouble() != 0){
				
			}
			
		} catch(IOException e){
			System.out.println("IO EXCEPTION: " + e.getMessage());
		} catch(ClassNotFoundException e){
			System.out.println("CLASS NOT FOUND EXCEPTION: " + e.getMessage());
		}
		try{
			//FileInputStream fip = new FileInputStream(f);
			FileInputStream fip = new FileInputStream("objects.txt");
			ObjectInputStream oip = new ObjectInputStream(fip);
			
			ArrayList<DShape> list = view.getShapes();
			list.clear();
			
			System.out.println("Reading");
			while(oip.readObject() != null){
				DShape shape = (DShape)oip.readObject();
				list.add(shape);
			}
			for(int i = 0; i < list.size(); i++){
				DShape shape = (DShape)oip.readObject();
				list.add(shape);
			}
			
			System.out.println("Done");
			oip.close();
			
		} catch(IOException e){
			System.out.println("IO EXCEPTION: " + e.getMessage());
		} catch(ClassNotFoundException e){
			System.out.println("CLASS NOT FOUND EXCEPTION: " + e.getMessage());
		}
	}*/
	
	public void save(File file, ArrayList<DShape> list) {
		/*Gson gson = new Gson();
		for (DShape dshape : list) {
		  String str = gson.toJson(dshape);
		  dshape.getClass().getName();
		  gson.fromJson(str, DShape.class);
		}*/
		if(file != null){
		try {
			System.out.println("Saving File");
			XMLEncoder xmlOut = new XMLEncoder(new BufferedOutputStream(new FileOutputStream(file)));

			ArrayList<DShapeModel> model = new ArrayList<>();
			
			for(DShape ds : list){
				DShapeModel m = ds.getShapeModel();
				//m.setShape(ds);
				model.add(m);
				//model.add(ds.getShapeModel());
			}
			
			DShapeModel[] shapeArray = model.toArray(new DShapeModel[0]);
			// Dump that whole array
			xmlOut.writeObject(shapeArray);
			// And we're done!
			xmlOut.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		}
	}

	public void open(File file) {
		DShapeModel[] shapeModel = null;
		
		if(file != null){
		try {
			System.out.println("Opening File");
			// Create an XMLDecoder around the file
			XMLDecoder xmlIn = new XMLDecoder(new BufferedInputStream(new FileInputStream(file)));
			// Read in the whole array of DotModels
			shapeModel = (DShapeModel[]) xmlIn.readObject();
			xmlIn.close();

			view.addShapes(shapeModel);
		} catch (IOException e) {
			e.printStackTrace();
		}
		}
	}
	
	public void saveImage(File f){
		if(f != null){
			try{
			
				WritableImage writableImage = new WritableImage(1920, 1080);
				view.canvas.snapshot(null, writableImage);
				RenderedImage renderedImage = SwingFXUtils.fromFXImage(writableImage, null);
				ImageIO.write(renderedImage, "png", f);
		
			} catch(IOException e){
				System.out.println(e.getMessage());
			}
		}
	}
}
