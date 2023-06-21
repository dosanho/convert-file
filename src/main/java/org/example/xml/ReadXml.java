package org.example.xml;

import org.example.dto.StudentDTO;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class ReadXml {
    public static void main(String[] args) {
        List<StudentDTO> listStudents;
        try {
            listStudents = ReadXml.readListStudents();

            // hiển thị các đối tượng student ra màn hình
            for (StudentDTO student : listStudents) {
                System.out.println(student.toString());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (XMLStreamException e) {
            e.printStackTrace();
        }
    }

    public static List<StudentDTO> readListStudents() throws XMLStreamException, FileNotFoundException {
        List<StudentDTO> listStudents = new ArrayList<>();
        StudentDTO student = null;
        String tagContent = null;

        File inputFile = new File("/Users/seu/Documents/telsoft/oss/convert-file/src/main/resources/file/student.xml");
        InputStream is = new FileInputStream(inputFile);
        XMLInputFactory factory = XMLInputFactory.newInstance();
        XMLStreamReader reader = factory.createXMLStreamReader(is);

        // duyệt các phần tử student
        while (reader.hasNext()) {
            int event = reader.next();

            switch (event) {
                case XMLStreamConstants.START_ELEMENT:
                    // tạo đối tượng student
                    if ("student".equals(reader.getLocalName())) {
                        student = new StudentDTO();
                        student.setId(reader.getAttributeValue(0));
                    }
                    break;

                case XMLStreamConstants.CHARACTERS:
                    // đọc nội dung của thẻ hiện tại
                    tagContent = reader.getText().trim();
                    break;

                case XMLStreamConstants.END_ELEMENT:
                    switch (reader.getLocalName()) {
                        case "student":
                            listStudents.add(student);
                            break;
                        case "firstname":
                            student.setFirstName(tagContent);
                            break;
                        case "lastname":
                            student.setLastName(tagContent);
                            break;
                        case "marks":
                            student.setMarks(tagContent);
                            break;
                    }
                    break;
            }
        }
        return listStudents;
    }
}

