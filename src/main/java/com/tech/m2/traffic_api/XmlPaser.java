package com.tech.m2.traffic_api;

import com.tech.m2.dto.DestPositonDto;
import com.tech.m2.dto.Traffic_SpotInfoDto;
import org.geotools.geometry.DirectPosition2D;
import org.geotools.referencing.CRS;
import org.geotools.referencing.ReferencingFactoryFinder;
import org.opengis.referencing.crs.CRSFactory;
import org.opengis.referencing.crs.CoordinateReferenceSystem;
import org.opengis.referencing.operation.MathTransform;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.StringReader;
import java.util.ArrayList;

public class XmlPaser {
    private ArrayList<Object> spotInfo_Parsing(String data,Object object) {
        ArrayList<Object> dtos = new ArrayList<>();
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(new InputSource(new StringReader(data)));

            NodeList result = doc.getElementsByTagName("RESULT");
            Element result_Node = (Element) result.item(0);
            String result_code = getElementValue(result_Node, "CODE");
            String result_massage = getElementValue(result_Node, "MESSAGE");
            System.out.println("호출결과 : " + result_massage);
            if (result_code != null && result_code.equals("INFO-000")) {

                NodeList rows = doc.getElementsByTagName("row");


                for (int i = 0; i < rows.getLength(); i++) {
                    Node row = rows.item(i);
                    Traffic_SpotInfoDto dto = new Traffic_SpotInfoDto();

                    if (row.getNodeType() == Node.ELEMENT_NODE) {
                        Element element = (Element) row;
                        Traffic_SpotInfoDto traffic_spotInfo = new Traffic_SpotInfoDto();
                        String spot_num = getElementValue(element, "spot_num");
                        String spot_nm = getElementValue(element, "spot_nm");
                        String grs_x = getElementValue(element, "grs80tm_x");
                        String grs_y = getElementValue(element, "grs80tm_y");
                        System.out.print("spot_num = " + spot_num);
                        System.out.print(", spot_nm = " + spot_nm);
                        System.out.print(", grs_x = " + grs_x);
                        System.out.println(", grs_y = " + grs_y);
                        DestPositonDto trans_pos = trans_destPosition(grs_x,grs_y);
                        System.out.println(trans_pos.getLatitude()+" 위도경도 "+trans_pos.getLongitude());
                        dto.setChk(1);
                        dto.setSpot_num(spot_num);
                        dto.setSpot_nm(spot_nm);
                        dto.setdPx_longitude(Double.toString(trans_pos.getLongitude()));
                        dto.setdPy_latitude(Double.toString(trans_pos.getLatitude()));
                        dtos.add(dto);
                    }
                }

            } else if (result_code != null && result_code.equals("INFO-200")) {
                System.out.println("api호출값이 null입니다.");
            } else {
                System.out.println("api호출과정에서 문제가 발생했습니다.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
//        if (dtos.get(0).getChk() == -1) {
//            System.out.println("api호출과정에서 문제가 발생했습니다.");
//        } else if (dtos.get(0).getChk() == 0) {
//            System.out.println("api호출값이 null입니다.");
//        }
        return dtos;
    }

    private static String getElementValue(Element parentElement, String tagName) {
        NodeList nodeList = parentElement.getElementsByTagName(tagName);
        if (nodeList.getLength() > 0) {
            Node node = nodeList.item(0);
            if (node.getFirstChild() != null) {
                return node.getFirstChild().getNodeValue();
            }
        }
        return null;
    }

    private static DestPositonDto trans_destPosition(String grs80tm_x, String grs80tm_y) {
        DestPositonDto dto = new DestPositonDto();
        try {
            // 문자열을 double로 변환
            double x = Double.parseDouble(grs80tm_x);
            double y = Double.parseDouble(grs80tm_y);

            // 좌표계 변환 설정
            String target5181 = "PROJCS[\"KGD2002 / Central Belt\","
                    + "GEOGCS[\"KGD2002\","
                    + "DATUM[\"Korean_Geodetic_Datum_2002\","
                    + "SPHEROID[\"GRS 1980\",6378137,298.257222101],"
                    + "TOWGS84[0,0,0,0,0,0,0]],"
                    + "PRIMEM[\"Greenwich\",0,AUTHORITY[\"EPSG\",\"8901\"]],"
                    + "UNIT[\"degree\",0.0174532925199433,AUTHORITY[\"EPSG\",\"9122\"]],"
                    + "AUTHORITY[\"EPSG\",\"4737\"]],"
                    + "PROJECTION[\"Transverse_Mercator\"],"
                    + "PARAMETER[\"latitude_of_origin\",38],"
                    + "PARAMETER[\"central_meridian\",127],"
                    + "PARAMETER[\"scale_factor\",1],"
                    + "PARAMETER[\"false_easting\",200000],"
                    + "PARAMETER[\"false_northing\",500000],"
                    + "UNIT[\"metre\",1,AUTHORITY[\"EPSG\",\"9001\"]],"
                    + "AUTHORITY[\"EPSG\",\"5181\"]]";
            CRSFactory crsFactory = ReferencingFactoryFinder.getCRSFactory(null);
            CoordinateReferenceSystem sourceCRS = null;
            sourceCRS = crsFactory.createFromWKT(target5181); // GRS80TM 좌표계를 CRS로 만들어냄
//            CoordinateReferenceSystem sourceCRS = CRS.decode("EPSG:5179"); // GRS80TM 좌표계

            String target4326 = "GEOGCS[\"WGS 84\","
                    + "DATUM[\"WGS_1984\","
                    + "SPHEROID[\"WGS 84\",6378137,298.257223563,AUTHORITY[\"EPSG\",\"7030\"]],"
                    + "AUTHORITY[\"EPSG\",\"6326\"]],"
                    + "PRIMEM[\"Greenwich\",0,AUTHORITY[\"EPSG\",\"8901\"]],"
                    + "UNIT[\"degree\",0.0174532925199433,AUTHORITY[\"EPSG\",\"9122\"]],"
                    + "AUTHORITY[\"EPSG\",\"4326\"]]";
            CoordinateReferenceSystem targetCRS = null;
            targetCRS = crsFactory.createFromWKT(target4326);  // WGS84 좌표계를 CRS로 만들어냄

//            CoordinateReferenceSystem targetCRS = CRS.decode("EPSG:4326"); // WGS84 좌표계
            //5179 => 4326
            MathTransform transform = CRS.findMathTransform(sourceCRS, targetCRS, true);

            // 변환 전 좌표
            DirectPosition2D srcPosition = new DirectPosition2D(sourceCRS, x, y);

            // 변환 후 좌표
            DirectPosition2D destPosition = new DirectPosition2D();
            transform.transform(srcPosition, destPosition);

            dto.setLongitude(destPosition.x);
            dto.setLatitude(destPosition.y);
            // 결과 출력
            System.out.println("Longitude: " + dto.getLongitude()); //경도
            System.out.println("Latitude: " + dto.getLatitude());  //위도

            return dto;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


}
