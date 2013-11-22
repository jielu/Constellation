package insect.gui.infos;

import insect.coverage.analysis.jaba.JABACoverage;

import java.util.ArrayList;
import java.util.Enumeration;

/**
 * PackageInfo.java
 *
 *
 * Created: Sun Sep 29 21:08:00 2002
 *
 * @author <a href="mailto:anil@cc.gatech.edu">Anil Chawla</a>
 */
public class PackageInfo extends InsectGUIInfo {

	//attributes
	private String name;
	private PackageInfo[] pkgs;
	private ClassInfo[] classes;
	private JABACoverage cov;

	/**
	 * Constructor when all attributes are
	 * already known.
	 *
	 * @param name package name
	 * @param classes ClassInfos for classes
	 * @param pkgs PackageInfos for subpackages
	 * @param cov program Coverage object
	 */
	public PackageInfo(
		String name,
		ClassInfo[] classes,
		PackageInfo[] pkgs,
		JABACoverage cov) {
		this.name = name;
		this.classes = classes;
		this.pkgs = pkgs;
		this.cov = cov;
	}

	/**
	 * Constructor to build PackageInfo based on
	 * JABA package object.
	 *
	 * @param pkg a <code>jaba.sym.Package</code> value
	 * @param cov program coverage object
	 */
	public PackageInfo(jaba.sym.Package pkg, JABACoverage cov) {

		name = pkg.getName();
		ArrayList p = new ArrayList();
		ArrayList c = new ArrayList();
		this.cov = cov;

		Enumeration subPkgs = pkg.getPackages();
		Enumeration pkgClasses = pkg.getClasses();

		//build PackageInfos for all subpackages
		while (subPkgs.hasMoreElements()) {

			Object o = subPkgs.nextElement();
			if (o instanceof jaba.sym.Package)
				p.add(new PackageInfo((jaba.sym.Package) o, cov));
		}

		//build ClassInfos for all classes
		while (pkgClasses.hasMoreElements()) {

			Object o = pkgClasses.nextElement();
			if (o instanceof jaba.sym.Class)
				c.add(new ClassInfo((jaba.sym.Class) o, cov));
		}

		//store package and class infos
		pkgs = (PackageInfo[]) p.toArray(new PackageInfo[p.size()]);
		classes = (ClassInfo[]) c.toArray(new ClassInfo[c.size()]);

	}

	/**
	 * Computes coverage for this package.
	 *
	 * @param execID execution to compute coverage for
	 * @param recompute if coverage should be recomputed
	 */
	public void compute(String execID, boolean recompute) {

		//load correct execution
		ExecInfo ei = getExec(execID);
		if ((ei != null) && (!recompute)) {
			numSt = ei.numSt;
			numCalls = ei.numCalls;
			numThrows = ei.numThrows;
			numCaught = ei.numCaught;
			covSt = ei.covSt;
			covCalls = ei.covCalls;
			covThrows = ei.covThrows;
			covCaught = ei.covCaught;
			return;
		}

		//reset counts
		numCalls = numSt = numThrows = numCaught = 0;
		covCalls = covSt = covThrows = covCaught = 0;

		//compute coverage for every package and combine
		for (int i = 0; i < pkgs.length; i++) {

			//get package coverage
			pkgs[i].compute(execID, recompute);

			//copy coverage stats
			numSt += pkgs[i].numSt;
			numCalls += pkgs[i].numCalls;
			numThrows += pkgs[i].numThrows;
			numCaught += pkgs[i].numCaught;
			covSt += pkgs[i].covSt;
			covCalls += pkgs[i].covCalls;
			covThrows += pkgs[i].covThrows;
			covCaught += pkgs[i].covCaught;
		}

		//compute coverage for every class and combine
		for (int i = 0; i < classes.length; i++) {

			//get class coverage
			classes[i].compute(execID, recompute);

			//copy coverage stats
			numSt += classes[i].numSt;
			numCalls += classes[i].numCalls;
			numThrows += classes[i].numThrows;
			numCaught += classes[i].numCaught;
			covSt += classes[i].covSt;
			covCalls += classes[i].covCalls;
			covThrows += classes[i].covThrows;
			covCaught += classes[i].covCaught;
		}

		//save values for execution
		setExec(
			execID,
			numCalls,
			numSt,
			numThrows,
			numCaught,
			covCalls,
			covSt,
			covThrows,
			covCaught);
	}

	/**
	 * Accessor for package name.
	 *
	 * @return a <code>String</code> value
	 */
	public String getName() {
		return name;
	}

	/**
	 * Accessor for subpackages.
	 *
	 * @return a <code>PackageInfo[]</code> value
	 */
	public PackageInfo[] getPackageInfos() {
		return pkgs;
	}

	/**
	 * Accessor for classes.
	 *
	 * @return a <code>ClassInfo[]</code> value
	 */
	public ClassInfo[] getClassInfos() {
		return classes;
	}

	/**
	 * Sets the subpackages for this package.
	 *
	 * @param pkgs a <code>PackageInfo[]</code> value
	 */
	public void setPackageInfos(PackageInfo[] pkgs) {
		this.pkgs = pkgs;
	}

	/**
	 * Sets the classes for this package.
	 *
	 * @param classes a <code>ClassInfo[]</code> value
	 */
	public void setClassInfos(ClassInfo[] classes) {
		this.classes = classes;
	}

	/**
	 * Returns the immediate name for this package.
	 *
	 * @return a <code>String</code> value
	 */
	public String toString() {

		if (name.lastIndexOf('/') > 0)
			return name.substring(name.lastIndexOf('/') + 1);
		else
			return name;
	}

} // PackageInfo
