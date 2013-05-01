package gwtcog.core.ml.bayesian.bif;
///*
// * Encog(tm) Core v3.2 - Java Version
// * http://www.heatonresearch.com/encog/
// * https://github.com/encog/encog-java-core
// 
// * Copyright 2008-2013 Heaton Research, Inc.
// *
// * Licensed under the Apache License, Version 2.0 (the "License");
// * you may not use this file except in compliance with the License.
// * You may obtain a copy of the License at
// *
// *     http://www.apache.org/licenses/LICENSE-2.0
// *
// * Unless required by applicable law or agreed to in writing, software
// * distributed under the License is distributed on an "AS IS" BASIS,
// * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// * See the License for the specific language governing permissions and
// * limitations under the License.
// *   
// * For more information on Heaton Research copyrights, licenses 
// * and trademarks visit:
// * http://www.heatonresearch.com/copyright
// */
//package gwtcog.core.ml.bayesian.bif;
//
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.OutputStream;
//
//import javax.xml.parsers.ParserConfigurationException;
//import javax.xml.parsers.SAXParser;
//import javax.xml.parsers.SAXParserFactory;
//
//import org.xml.sax.SAXException;
//
//import gwtcog.core.shared.org.encog.Encog;
//import gwtcog.core.ml.bayesian.BayesianError;
//import gwtcog.core.ml.bayesian.BayesianEvent;
//import gwtcog.core.ml.bayesian.BayesianNetwork;
//import gwtcog.core.ml.bayesian.table.TableLine;
//import gwtcog.core.util.csv.CSVFormat;
//
///**
// * A utility class to read and write Bayesian networks in BIF format.
// * 
// * http://www.heatonresearch.com/wiki/Bayesian_Interchange_Format
// */
//public class BIFUtil {
//
//	/**
//	 * Read a BIF file.
//	 * 
//	 * @param f
//	 *            The BIF file.
//	 * @return The Bayesian network that was read.
//	 */
//	public static BayesianNetwork readBIF(String f) {
//		return readBIF(new File(f));
//	}
//
//	public static BayesianNetwork readBIF(File f) {
//		FileInputStream fis = null;
//
//		try {
//			fis = new FileInputStream(f);
//			return readBIF(fis);
//		} catch (IOException ex) {
//			throw new BayesianError(ex);
//		} finally {
//			if (fis != null) {
//				try {
//					fis.close();
//				} catch (IOException ex) {
//					// who cares at this point.
//				}
//			}
//		}
//	}
//
//	/**
//	 * Read a BIF file from a stream.
//	 * 
//	 * @param is
//	 *            The stream to read from.
//	 * @return The Bayesian network read.
//	 */
//	public static BayesianNetwork readBIF(InputStream is) {
//		try {
//			BIFHandler h = new BIFHandler();
//			SAXParserFactory spf = SAXParserFactory.newInstance();
//			SAXParser sp = spf.newSAXParser();
//			sp.parse(is, h);
//			return h.getNetwork();
//		} catch (IOException ex) {
//			throw new BayesianError(ex);
//		} catch (ParserConfigurationException ex) {
//			throw new BayesianError(ex);
//		} catch (SAXException ex) {
//			throw new BayesianError(ex);
//		}
//	}
//
//	/**
//	 * Write a Bayesian network to BIF form.
//	 * 
//	 * @param fn
//	 *            The file name to save to.
//	 * @param network
//	 *            The network to save.
//	 */
//	public static void writeBIF(String fn, BayesianNetwork network) {
//		writeBIF(new File(fn), network);
//	}
//
//	/**
//	 * Write a Bayesian network to a BIF file.
//	 * 
//	 * @param file
//	 *            The file to save to.
//	 * @param network
//	 *            The network to save.
//	 */
//	public static void writeBIF(File file, BayesianNetwork network) {
//		//disabled
////		FileOutputStream fos = null;
////		try {
////			fos = new FileOutputStream(file);
////			writeBIF(fos, network);
////		} catch (IOException ex) {
////			throw new BayesianError(ex);
////		} finally {
////			if (fos != null) {
////				try {
////					fos.close();
////				} catch (IOException e) {
////					// don't care at this point
////				}
////			}
////		}
//	}
//
//	/**
//	 * Write a Bayesian network to an output stream in BIF format.
//	 * 
//	 * @param os
//	 *            The output stream to write to.
//	 * @param network
//	 *            The network to write.
//	 */
//	public static void writeBIF(OutputStream os, BayesianNetwork network) {
//		//disabled
//		//		WriteXML xml = new WriteXML(os);
////		xml.beginDocument();
////		xml.addAttribute("VERSION", "0.3");
////		xml.beginTag("BIF");
////		xml.beginTag("NETWORK");
////		xml.addProperty("NAME", "Bayes Network, Generated by Encog");
////		// write variables
////		for (BayesianEvent event : network.getEvents()) {
////			xml.addAttribute("TYPE", "nature");
////			xml.beginTag("VARIABLE");
////			xml.addProperty("NAME", event.getLabel());
////			for (BayesianChoice str : event.getChoices()) {
////				xml.addProperty("OUTCOME", str.getLabel());
////			}
////			xml.endTag();
////		}
////
////		// write relations
////		for (BayesianEvent event : network.getEvents()) {
////			xml.beginTag("DEFINITION");
////			xml.addProperty("FOR", event.getLabel());
////			for (BayesianEvent parentEvent : event.getParents()) {
////				xml.addProperty("GIVEN", parentEvent.getLabel());
////			}
////			xml.addAttribute("TABLE", generateTable(event));
////			xml.endTag();
////		}
////
////		xml.endTag();
////		xml.endTag();
////		xml.endDocument();
//
//	}
//
//	/**
//	 * Generate a table, in BIF format.
//	 * 
//	 * @param event
//	 *            The event to write.
//	 * @return The string form of the table.
//	 */
//	public static String generateTable(BayesianEvent event) {
//		StringBuilder s = new StringBuilder();
//		int tableIndex = 0;
//		int[] args = new int[event.getParents().size()];
//		do {
//			for (int result = 0; result < event.getChoices().size(); result++) {
//				TableLine line = event.getTable().findLine(result, args);
//				if (s.length() > 0) {
//					s.append(" ");
//				}
//				s.append(CSVFormat.EG_FORMAT.format(line.getProbability(),
//						Encog.DEFAULT_PRECISION));
//			}
//		} while (BIFUtil.rollArgs(event, args));
//		return s.toString();
//	}
//
//	/**
//	 * Iterate through the event arguments in the BIF way, which is different
//	 * than Encog's method.
//	 * 
//	 * @param event
//	 *            The event to save.
//	 * @param args
//	 *            The arguments.
//	 * @return True if there is further to iterate.
//	 */
//	public static boolean rollArgs(BayesianEvent event, int[] args) {
//		int currentIndex = event.getParents().size() - 1;
//		boolean done = false;
//		boolean eof = false;
//
//		if (event.getParents().size() == 0) {
//			done = true;
//			eof = true;
//		}
//
//		while (!done) {
//			int v = (int) args[currentIndex];
//			v++;
//			if (v >= event.getParents().get(currentIndex).getChoices().size()) {
//				args[currentIndex] = 0;
//			} else {
//				args[currentIndex] = v;
//				done = true;
//				break;
//			}
//
//			currentIndex--;
//
//			if (currentIndex < 0) {
//				done = true;
//				eof = true;
//			}
//		}
//
//		return !eof;
//	}
//
//}
