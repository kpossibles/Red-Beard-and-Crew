package chronotimer;
import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
 
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class Exporter extends ChronoTimer{
	private Run run;
	
	public Exporter(Run _run){
		run = _run;
		
		DocumentBuilderFactory icFactory = DocumentBuilderFactory.newInstance();
	    DocumentBuilder icBuilder;
    
	    try {
	        icBuilder = icFactory.newDocumentBuilder();
	        Document doc = icBuilder.newDocument();
	        String mainRootName = String.format("Run_%03d", run.getId());
	        Element mainRootElement = doc.createElementNS(null, mainRootName);
	        doc.appendChild(mainRootElement);
	
	        // append child elements to root element
	        for(Racer racer:run.getRacers()){
	        	mainRootElement.appendChild(getRacer(doc, racer));
	        }
	        
	        // write the content into xml file
			Transformer transformer = TransformerFactory.newInstance().newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes"); 
			DOMSource src = new DOMSource(doc);
			String filename = String.format("Run%03d.xml", run.getId());
			StreamResult result = new StreamResult(new File("usb/"+filename));

			// Output to console for testing
//			 StreamResult testresult = new StreamResult(System.out);

			transformer.transform(src, result);
//			transformer.transform(src, testresult);

			System.out.println("File saved!");
	        System.out.println("\nXML DOM Created Successfully.");
	
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
    }
	
	private Node getRacer(Document doc, Racer racer) {
	    Element exportedRacer = doc.createElement("Racer");
	    Long totalTime=racer.getTimeAsLong();
	    String totalTimeStr=racer.getTime();
	    if(totalTime<0){
	    	if(totalTime==-1){
	    		totalTimeStr="DNF";
	    	}
	    	else if(totalTime==-2){
	    		totalTimeStr="00:00:00.0";
	    	}
	    	else{
	    		System.out.println("Error in exporting racer");
	    	}
	    }
	    String idName = String.format("%03d_lane%02d", run.getId(), racer.getLane());
	    exportedRacer.setAttribute("id", idName);
	    exportedRacer.appendChild(getRacerElements(doc, exportedRacer, "Racer", ""+racer.getId()));
	    exportedRacer.appendChild(getRacerElements(doc, exportedRacer, "Start", racer.getStartTime()));
	    exportedRacer.appendChild(getRacerElements(doc, exportedRacer, "Finish", racer.getFinishTime()));
	    exportedRacer.appendChild(getRacerElements(doc, exportedRacer, "Total_Time", totalTimeStr));
	    return exportedRacer;
	}
	
	// utility method to create text node
	private Node getRacerElements(Document doc, Element exportedRun, String name, String value) {
	    Element node = doc.createElement(name);
	    node.appendChild(doc.createTextNode(value));
	    return node;
	}
	
	public static void main(String[]args){
		Run r = new Run(1);
		for(int i=0;i<8;i++){
			Racer temp = new Racer(i);
			temp.setStart(new Timer().getTime());
			temp.setFinish(new Timer().getTime()+1000+i);
			temp.setLane(i+1);
			r.add(temp);
		}
		new Exporter(r);
		System.exit(0);
		
	}
}
