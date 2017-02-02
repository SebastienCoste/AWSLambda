package fr.sco.staticjo.lambda;


import java.util.stream.IntStream;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.amazonaws.services.lambda.runtime.Context;

import fr.sco.staticjo.genetic.bestpath.Point;
import fr.sco.staticjo.lambda.PopulationDTO;
import fr.sco.staticjo.lambda.WorldMapDTO;
import fr.sco.staticjo.lambda.WorldMapLambda;


public class WorldMapLambdaTest {

	
	private static final int size = 10;
	
	private static PopulationDTO input;
	private static Context context;
	private static WorldMapLambda lambda;
	
	
	@BeforeClass
	public static void init(){
		lambda = new WorldMapLambda();
		
		input = new PopulationDTO();
		input.setPopSize(size);
		WorldMapDTO[] person = new WorldMapDTO[size];
		Point[] pointList = new Point[size];
		Long[] genes = new Long[size];
		
		IntStream.range(0, size).forEach(e -> {
			genes[e] = Long.valueOf(e);
			person[e] = new WorldMapDTO().withGenes(genes);
			pointList[e] = new Point(e, e, e);
		});
		input.setPerson(person);
		input.setPointList(pointList );
		
		context = null;
	}
	
	

	@Test
	public void testHandleRequest() {
		
		String result = lambda.handleRequest(input, context);
		Assert.assertNotNull(result);
		
	}
}
