package myMod.common;

import java.util.*;

public class Perlin
{
	public Perlin()
	{

	}

	public double[][] GenerateWhiteNoise(int width, int height)
	{
	    Random random = new Random(); //Seed to 0 for testing
	    double[][] noise = new double[width][height];
	 
	    for (int i = 0; i < width; i++)
	    {
	        for (int j = 0; j < height; j++)
	        {
	            noise[i][j] = random.nextInt(2);
	        }
	    }
	    return noise;
	}

	public double[][] GenerateSmoothNoise(double[][] baseNoise, int octave)
	{
	   int width = baseNoise.length;
	   int height = baseNoise[0].length;
	 
	   double[][] smoothNoise = new double[width][height];
	 
	   int samplePeriod = 1 << octave; // calculates 2 ^ k
	   double sampleFrequency = 1.0 / samplePeriod;
	 
	   for (int i = 0; i < width; i++)
	   {
	      //calculate the horizontal sampling indices
	      int sample_i0 = (i / samplePeriod) * samplePeriod;
	      int sample_i1 = (sample_i0 + samplePeriod) % width; //wrap around
	      double horizontal_blend = (i - sample_i0) * sampleFrequency;
	 
	      for (int j = 0; j < height; j++)
	      {
	         //calculate the vertical sampling indices
	         int sample_j0 = (j / samplePeriod) * samplePeriod;
	         int sample_j1 = (sample_j0 + samplePeriod) % height; //wrap around
	         double vertical_blend = (j - sample_j0) * sampleFrequency;
	 
	         //blend the top two corners
	         double top = Interpolate(baseNoise[sample_i0][sample_j0],
	            baseNoise[sample_i1][sample_j0], horizontal_blend);
	 
	         //blend the bottom two corners
	         double bottom = Interpolate(baseNoise[sample_i0][sample_j1],
	            baseNoise[sample_i1][sample_j1], horizontal_blend);
	 
	         //final blend
	         smoothNoise[i][j] = Interpolate(top, bottom, vertical_blend);
	      }
	   }
	   return smoothNoise;
	}

	public double Interpolate(double x0, double x1, double alpha)
	{
	   return x0 * (1 - alpha) + alpha * x1;
	}

	public double[][] GeneratePerlinNoise(double[][] baseNoise, int octaveCount, double persistance)
	{
	   int width = baseNoise.length;
	   int height = baseNoise[0].length;
	 
	   double[][][] smoothNoise = new double[octaveCount][][]; //an array of 2D arrays containing
	 
	   //double persistance = 0.5;
	 
	   //generate smooth noise
	   for (int i = 0; i < octaveCount; i++)
	   {
	       smoothNoise[i] = GenerateSmoothNoise(baseNoise, i);
	   }
	 
	    double[][] perlinNoise = new double[width][height];
	    double amplitude = 1.0;
	    double totalAmplitude = 0.0;
	 
	    //blend noise together
	    for (int octave = octaveCount - 1; octave >= 0; octave--)
	    {
	       amplitude *= persistance;
	       totalAmplitude += amplitude;
	 
	       for (int i = 0; i < width; i++)
	       {
	          for (int j = 0; j < height; j++)
	          {
	             perlinNoise[i][j] += smoothNoise[octave][i][j] * amplitude;
	          }
	       }
	    }
	 
	   //normalisation
	   for (int i = 0; i < width; i++)
	   {
	      for (int j = 0; j < height; j++)
	      {
	         perlinNoise[i][j] /= totalAmplitude;
	      }
	   }
	 
	   return perlinNoise;
	}
}
