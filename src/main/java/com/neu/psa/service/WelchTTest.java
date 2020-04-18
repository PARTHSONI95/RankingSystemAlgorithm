package com.neu.psa.service;

import org.apache.commons.math3.distribution.TDistribution;

import com.neu.psa.pojo.Team;

/**
 * 
 *
 */
public class WelchTTest {


	public static double[] meanvar(double[] a) {
		double m = 0.0, v = 0.0;
		int n = a.length;

		for (double x: a) {
			m += x;
		}
		m /= n;

		for (double x: a) {
			v += (x - m) * (x - m);
		}
		v /= (n - 1);

		return new double[] {m, v};

	}
	/**
	 * <h1>T-test using Welch Analysis</h1>
	 * <p>Using driver - commons-math3 to deal with T distribution considering continuous attributes like Average, RPO in Cricket</p>
	 * 
	 * 
	 * @param team
	 * @param totalMeanOfAllTeam
	 * @param variance
	 * @param totalCount
	 * @param param
	 * @return
	 */
	public static double welch_ttest(Team team,double totalMeanOfAllTeam, double variance,int totalCount,String param) {
		double mx=0.0, my=0.0, vx=0.0, vy=0.0, t, df, p;

		int nx = team.getTeamSeriesCount();
		int ny = totalCount;
		if(param.equals("Average")) {
			mx = team.getMean_Of_Average();
			vx = Math.pow(team.getStdDeviation_ForAverage(),2);
		}
		if(param.equals("RPO")) {
			mx = team.getMean_Of_RPO();
			vx = Math.pow(team.getStdDeviation_ForRPO(),2);  
		}
		my = totalMeanOfAllTeam;
		vy = variance;
		t = (mx-my)/Math.sqrt(vx/nx+vy/ny);
		df = Math.pow(vx/nx+vy/ny, 2)/(vx*vx/(nx*nx*(nx-1))+vy*vy/(ny*ny*(ny-1)));

		TDistribution dist = new TDistribution(df);
		p = 2.0 * dist.cumulativeProbability(-Math.abs(t));
		team.settProbByAverage(p);
		return p;
	}
	
	public static double[] welch_ttest(double[] x, double[] y) {
		double mx, my, vx, vy, t, df, p;
		double[] res;
		int nx = 1, ny = 8;

		res = meanvar(x);
		//mx = res[0];
		//vx = res[1];
		mx = 26.34;
		vx = 11.69;

		res = meanvar(y);
		//my = res[0];
		//vy = res[1];
		my = 38.03;
		vy = 9.60;

		t = (mx-my)/Math.sqrt(vx/nx+vy/ny);
		//df = Math.pow(vx/nx+vy/ny, 2)/(vx*vx/(nx*nx*(nx-1))+vy*vy/(ny*ny*(ny-1)));

		df = 7;
		TDistribution dist = new TDistribution(df);
		p = 2.0*dist.cumulativeProbability(-Math.abs(t));
		return new double[] {t, df, p};
	}
	
	/**
	 * Driver stub to test the T-distribution for different values
	 * @param args
	 */
	public static void main(String[] args) {
		double x[] = {10,20,30,41};
		System.out.println(x.length);
		double y[] = {490.2};
		double res[] = welch_ttest(x, y);
		System.out.println("t = " + res[0]);
		System.out.println("df = " + res[1]);
		System.out.println("p = " + res[2]);

	}

}
