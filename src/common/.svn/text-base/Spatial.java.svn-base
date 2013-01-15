package common;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.xml.parsers.ParserConfigurationException;

import org.openrdf.OpenRDFException;
import org.policygrid.ontologies.FOAF;
import org.xml.sax.SAXException;
import net.sf.json.*;

/**
 * @author EP
 * @version 1.0
 */
public class Spatial {

	private Connections con;

	public Spatial() {
		con = new Connections();
		try {
			con.connect();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public int addSpatialSeries(String name, String resourceID,
			String seriesType, String featureType)
			throws InstantiationException, IllegalAccessException,
			ClassNotFoundException, SQLException {

		int seriesID = -1;

		String qry = "INSERT INTO spatial_data_series(name,resource_id,series_type,feature_type) values (?,?,?,?)";

		PreparedStatement pstmt = con.getCon().prepareStatement(qry);
		pstmt.setString(1, name);
		pstmt.setString(2, resourceID);
		pstmt.setString(3, seriesType);
		pstmt.setString(4, featureType);

		pstmt.executeUpdate();
		ResultSet rs = pstmt.getGeneratedKeys();

		if (rs.next()) {
			seriesID = rs.getInt(1);
		}

		pstmt.close();

		return seriesID;

	}

	public int addSpatialFeature(int seriesID, String name,
			Double numericalValue, String textValue)
			throws InstantiationException, IllegalAccessException,
			ClassNotFoundException, SQLException {

		int featureID = -1;

		String qry = "INSERT INTO spatial_features(series_id,name,numerical_value,text_value) values (?,?,?,?)";
		PreparedStatement pstmt = con.getCon().prepareStatement(qry);
		pstmt.setInt(1, seriesID);
		pstmt.setString(2, name);
		pstmt.setDouble(3, numericalValue.doubleValue());
		pstmt.setString(4, textValue);
		pstmt.executeUpdate();
		ResultSet rs = pstmt.getGeneratedKeys();

		if (rs.next()) {
			featureID = rs.getInt(1);
		}

		pstmt.close();

		return featureID;

	}

	public int addGeoreferenceNE(int featureID, String geonamesID,
			double easting, double northing) throws InstantiationException,
			IllegalAccessException, ClassNotFoundException, SQLException {

		int referenceID = -1;

		String qry = "INSERT INTO georeference_ne(feature_id,geonames_id,northing,easting) values (?,?,?,?)";

		PreparedStatement pstmt = con.getCon().prepareStatement(qry);
		pstmt.setInt(1, featureID);
		pstmt.setString(2, geonamesID);
		pstmt.setDouble(3, northing);
		pstmt.setDouble(4, easting);

		pstmt.executeUpdate();
		ResultSet rs = pstmt.getGeneratedKeys();

		if (rs.next()) {
			referenceID = rs.getInt(1);
		}

		pstmt.close();

		return referenceID;

	}
	
	public ArrayList<MapDataSeries> listMapDataSeriesByUserID(
			int userid) throws InstantiationException,
			IllegalAccessException, ClassNotFoundException, SQLException {

		ArrayList<MapDataSeries> list = new ArrayList<MapDataSeries>();

		Statement st = con.getCon().createStatement();

		String qry = "select * from spatial_data_series, files where spatial_data_series.resource_id = files.resourceID and files.userid = ?";

		PreparedStatement pstmt = con.getCon().prepareStatement(qry);
		pstmt.setInt(1, userid);

		ResultSet rs = pstmt.executeQuery();
		while (rs.next()) {
			MapDataSeries ds = new MapDataSeries();
			ds.setId(rs.getInt("spatial_data_series.series_id"));
			ds.setName(rs.getString("spatial_data_series.name"));
			ds.setResourceID(rs.getString("spatial_data_series.resource_id"));
			ds.setFtype(rs.getString("spatial_data_series.feature_type"));
			ds.setStype(rs.getString("spatial_data_series.series_type"));
			list.add(ds);
		}

		return list;

	}

	public ArrayList<MapDataSeries> listMapDataSeriesByResurceID(
			String resourceID) throws InstantiationException,
			IllegalAccessException, ClassNotFoundException, SQLException {

		ArrayList<MapDataSeries> list = new ArrayList<MapDataSeries>();

		Statement st = con.getCon().createStatement();

		String qry = "select * from spatial_data_series where resource_id=?";

		PreparedStatement pstmt = con.getCon().prepareStatement(qry);
		pstmt.setString(1, resourceID);

		ResultSet rs = pstmt.executeQuery();
		while (rs.next()) {
			MapDataSeries ds = new MapDataSeries();
			ds.setId(rs.getInt("series_id"));
			ds.setName(rs.getString("name"));
			ds.setResourceID(rs.getString("resource_id"));
			list.add(ds);
		}

		return list;

	}

	public MapDataSeries getMapDataSeriesBySeriesID(int seriesID)
			throws InstantiationException, IllegalAccessException,
			ClassNotFoundException, SQLException {

		MapDataSeries series = new MapDataSeries();

		Statement st = con.getCon().createStatement();

		String qry = "select * from spatial_data_series where series_id=?";

		PreparedStatement pstmt = con.getCon().prepareStatement(qry);
		pstmt.setInt(1, seriesID);

		ResultSet rs = pstmt.executeQuery();
		while (rs.next()) {
			series.setId(rs.getInt("series_id"));
			series.setName(rs.getString("name"));
			series.setResourceID(rs.getString("resource_id"));

			ArrayList features = listMapFeaturesBySeriesID(rs.getInt("series_id"),rs.getString("feature_type"));
			series.setFeatures(features);
		}

		return series;

	}

	public double getMaxSeriesValueBySeriesID(int seriesID) throws SQLException {
		double max = 0;
		
		Statement st = con.getCon().createStatement();

			String qry = "select MAX(numerical_value) as maximum from spatial_features where series_id=? group by numerical_value";

			PreparedStatement pstmt = con.getCon().prepareStatement(qry);
			pstmt.setInt(1, seriesID);

			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				max = rs.getDouble("maximum");
			}
			
		return max;
	}
	
	public ArrayList<MapFeature> listMapFeaturesBySeriesID(int seriesID,
			String featureType) throws InstantiationException,
			IllegalAccessException, ClassNotFoundException, SQLException {

		ArrayList<MapFeature> list = new ArrayList();
		
		double max = getMaxSeriesValueBySeriesID(seriesID);

		Statement st = con.getCon().createStatement();

		if (featureType.equals("N")) {

			String qry = "select * from spatial_features, georeference_ne  where spatial_features.feature_id = georeference_ne.feature_id and spatial_features.series_id=?";

			PreparedStatement pstmt = con.getCon().prepareStatement(qry);
			pstmt.setInt(1, seriesID);

			ResultSet rs = pstmt.executeQuery();
			int fid = -1;

			MapFeature mf = null;
			ArrayList points = null;

			while (rs.next()) {

				if (fid != rs.getInt("spatial_features.feature_id")) {
					if (mf != null) {
						mf.setPoints(points);
						list.add(mf);
					}
					mf = new MapFeature();
					points = new ArrayList();
					mf.setId(rs.getInt("spatial_features.feature_id"));
					mf.setName(rs.getString("spatial_features.name"));
					mf.setNumericalValue(rs.getDouble("spatial_features.numerical_value") / max);
					mf.setTextValue("spatial_features.text_value");
					MapPoint mp = new MapPoint(rs
							.getDouble("georeference_ne.easting"), rs
							.getDouble("georeference_ne.northing"));
					points.add(mp);
					common.Utility.log.debug("getting feature : "
							+ rs.getString("spatial_features.name"));
				} else {
					MapPoint mp = new MapPoint(rs
							.getDouble("georeference_ne.easting"), rs
							.getDouble("georeference_ne.northing"));
					points.add(mp);
				}

				fid = rs.getInt("spatial_features.feature_id");
			}
			if (mf != null) {
				mf.setPoints(points);
				list.add(mf);
			}

		}
		
		
		// TODO: add additional conversions
		// if (featureType.equals("L")) for lat lon
		// if (featureType.equals("P")) for postcode
		// if (featureType.equals("S")) for place name string
		
		
		return list;

	}
}