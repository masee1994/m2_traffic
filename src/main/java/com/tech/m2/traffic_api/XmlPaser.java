package com.tech.m2.traffic_api;

import com.tech.m2.dto.DestPositonDto;
import com.tech.m2.dto.Traffic_AccInfoDto;
import com.tech.m2.dto.Traffic_SpotInfoDto;
import com.tech.m2.dto.WeatherDto;
import org.geotools.geometry.DirectPosition2D;
import org.geotools.referencing.CRS;
import org.geotools.referencing.ReferencingFactoryFinder;
import org.json.JSONArray;
import org.json.JSONObject;
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
    public <T> ArrayList<T> Traffic_Xml_Parsing(String data, String object, Class<T> tClass) {
        ArrayList<T> dtos = new ArrayList<>();
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

            switch (object) {

                case "spotInfo" :
                for (int i = 0; i < rows.getLength(); i++) {
                    Node row = rows.item(i);
                    Traffic_SpotInfoDto dto = new Traffic_SpotInfoDto();

                    if (row.getNodeType() == Node.ELEMENT_NODE) {
                        Element element = (Element) row;
                        String spot_num = getElementValue(element, "spot_num");
                        String spot_nm = getElementValue(element, "spot_nm");
                        String grs_x = getElementValue(element, "grs80tm_x");
                        String grs_y = getElementValue(element, "grs80tm_y");
                        System.out.print("spot_num = " + spot_num);
                        System.out.print(", spot_nm = " + spot_nm);
                        System.out.print(", grs_x = " + grs_x);
                        System.out.println(", grs_y = " + grs_y);
                        DestPositonDto trans_pos = trans_destPosition(grs_x, grs_y);
                        System.out.println(trans_pos.getLatitude() + " 위도경도 " + trans_pos.getLongitude());
                        dto.setChk(1);
                        dto.setSpot_num(spot_num);
                        dto.setSpot_nm(spot_nm);
                        dto.setdPx_longitude(Double.toString(trans_pos.getLongitude()));
                        dto.setdPy_latitude(Double.toString(trans_pos.getLatitude()));
                        dtos.add(tClass.cast(dto));
                    }
                }
                break;


                case "accInfo" :
                    for (int i = 0; i < rows.getLength(); i++) {
                        Node row = rows.item(i);
                        Traffic_AccInfoDto dto = new Traffic_AccInfoDto();
                        if (row.getNodeType() == Node.ELEMENT_NODE) {
                            Element element = (Element) row;
                            String grs_x = getElementValue(element, "grs80tm_x");
                            String grs_y = getElementValue(element, "grs80tm_y");
                            DestPositonDto trans_pos = trans_destPosition(grs_x, grs_y);
                            dto.setChk(1);
                            dto.setAcc_id(getElementValue(element, "acc_id"));
                            dto.setOccr_date(getElementValue(element, "occr_date"));
                            dto.setOccr_time(getElementValue(element, "occr_time"));
                            dto.setExp_clr_date(getElementValue(element, "exp_clr_date"));
                            dto.setExp_clr_time(getElementValue(element, "exp_clr_time"));
                            dto.setAcc_type(getElementValue(element, "acc_type"));
                            dto.setAcc_dtype(getElementValue(element, "acc_dtype"));
                            dto.setLink_id(getElementValue(element, "link_id"));
                            dto.setAcc_info(getElementValue(element, "acc_info"));
                            dto.setAcc_road_code(getElementValue(element, "acc_road_code"));
                            dto.setdPx_longitude(Double.toString(trans_pos.getLongitude()));
                            dto.setdPy_latitude(Double.toString(trans_pos.getLatitude()));

                            System.out.println("번호 : "+dto.getAcc_road_code());
                            System.out.print("날짜 : " + dto.getExp_clr_date());
                            System.out.print(", 시간 = " + dto.getExp_clr_time() +"\n");
                            System.out.println(dto.getAcc_info() );
                            System.out.print("경도(x) " + dto.getdPx_longitude());
                            System.out.println(", 위도(y) = " + dto.getdPy_latitude());
                            dtos.add(tClass.cast(dto));
                        }
                    }
                    break;

            }

            } else if (result_code != null && result_code.equals("INFO-200")) {
                System.out.println("api호출값이 null입니다.");
            } else {
                System.out.println("api호출과정에서 문제가 발생했습니다.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return dtos;
    }




    public WeatherDto Weather_Xml_Parsing(String data) {
        WeatherDto dto = new WeatherDto();
        // JSON 파싱
        JSONObject jsonObject = new JSONObject(data);

        // 날씨 정보 추출
        JSONArray weatherArray = jsonObject.getJSONArray("weather");
        JSONObject weatherObject = weatherArray.getJSONObject(0);
        String weatherMain = weatherObject.getString("main");
        String weatherDescription = weatherObject.getString("description");

        // 온도 정보 추출 및 섭씨로 변환
        JSONObject mainObject = jsonObject.getJSONObject("main");
        double tempKelvin = mainObject.getDouble("temp");
        double tempCelsius = Math.round(kelvinToCelsius(tempKelvin)*100)/100;

        // 풍향 및 풍속 정보 추출
        JSONObject windObject = jsonObject.getJSONObject("wind");
        double windSpeed = windObject.getDouble("speed");
        int windDeg = windObject.getInt("deg");

        // 출력
        System.out.println("날씨: " + weatherMain + " (" + weatherDescription + ")");
        System.out.println("온도: " + tempCelsius + " °C");
        System.out.println("풍속: " + windSpeed + " m/s");
        System.out.println("풍량: " + getWindDirection(windDeg) + "°");

        dto.setWeatherMain(weatherMain);
        dto.setWeatherDescription(weatherDescription);
        dto.setTempCelsius(Double.toString(tempCelsius));
        dto.setWindSpeed(Double.toString(windSpeed));
        dto.setWindDeg_origin(Integer.toString(windDeg));
        dto.setWindDeg(getWindDirection(windDeg));

        return dto;
    }

    private static double kelvinToCelsius(double kelvin) {
        return kelvin - 273.15;
    }

    private static String getWindDirection(int degrees) {
        String[] directions = {
                "북", "북북동", "북동", "동북동", "동", "동남동", "남동", "남남동",
                "남", "남남서", "남서", "서남서", "서", "서북서", "북서", "북북서"
        };
        int index = (int)((degrees + 11.25) / 22.5);
        return directions[index % 16];
    }

    //        if (dtos.get(0).getChk() == -1) {
//            System.out.println("api호출과정에서 문제가 발생했습니다.");
//        } else if (dtos.get(0).getChk() == 0) {
//            System.out.println("api호출값이 null입니다.");
//        }

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
//            System.out.println("Longitude: " + dto.getLongitude()); //경도
//            System.out.println("Latitude: " + dto.getLatitude());  //위도

            return dto;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


}
