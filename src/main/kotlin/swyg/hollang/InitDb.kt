package swyg.hollang

import jakarta.annotation.PostConstruct
import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext
import org.apache.poi.ss.usermodel.Workbook
import org.apache.poi.ss.usermodel.WorkbookFactory
import org.springframework.context.annotation.Profile
import org.springframework.core.io.ClassPathResource
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import swyg.hollang.entity.*
import java.io.InputStream

@Component
@Profile(value = ["local"])
class InitDb(private val initService: InitService) {

    @PostConstruct
    fun init() {
        initService.initTestData(1)
        initService.initHobbyTypeData()
        initService.initHobbyData()
    }
}

@Component
@Transactional
@Profile(value = ["local"])
class InitService {

    @PersistenceContext
    private lateinit var em: EntityManager

    private final val INIT_DATA_PATH = "/static/initData.xlsx"
    private final val IMG_URL = "https://test.com"

    fun initFile(): Workbook {
        val file = ClassPathResource(INIT_DATA_PATH).file
        val inputStream: InputStream = file.inputStream()

        return WorkbookFactory.create(inputStream)
    }

    fun initTestData(testVersion: Long) {
        val workbook = initFile()

        val sheet = workbook.getSheet("test")

        val questions: MutableSet<Question> = mutableSetOf()
        for (rowIndex in 1..sheet.lastRowNum) {
            val row = sheet.getRow(rowIndex)
            if(row.getCell(0) == null) break

            val number = rowIndex.toLong()
            val content = row.getCell(0).stringCellValue
            val answers: MutableSet<Answer> = mutableSetOf()
            for (cellIndex in row.firstCellNum + 1..row.firstCellNum + 2) {
                val cell = row.getCell(cellIndex)
                val cellValue = cell?.stringCellValue ?: ""
                val answer = Answer(cellIndex.toLong(), cellValue)
                answers.add(answer)
            }
            val question = Question(number, content, answers)
            questions.add(question)
        }
        //cascade type을 all로 해놨으니 영속성이 전이돼서 부모 엔티티를 영속화시키면 자식 엔티티도 영속화된다.
        val test = Test(testVersion, questions)
        em.persist(test)
    }

    fun initHobbyData() {
        val workbook = initFile()

        val sheet = workbook.getSheet("hobby")
        for (rowIndex in 1..sheet.lastRowNum) {
            val row = sheet.getRow(rowIndex)
            if(row.getCell(0) == null) break

            val name = row.getCell(0).stringCellValue
            val summary = row.getCell(1).stringCellValue
            val description = row.getCell(2).stringCellValue
            val imageName = row.getCell(3).stringCellValue
            val imageUrl = "${IMG_URL}/images/hobby/$imageName.png"
            val hobby = Hobby(name, summary, description, imageUrl)
            em.persist(hobby)
        }
    }

    fun initHobbyTypeData() {
        val workbook = initFile()

        val sheet = workbook.getSheet("hobby_type")
        for (rowIndex in 1..sheet.lastRowNum) {
            val row = sheet.getRow(rowIndex)
            if(row.getCell(0) == null) break

            val name = row.getCell(0).stringCellValue
            val description = row.getCell(1).stringCellValue
            val mbtiType = row.getCell(2).stringCellValue
            val imageUrl = "${IMG_URL}/images/hobby_type/${mbtiType}.png"
            val fitHobbyTypes = mutableListOf(
                row.getCell(3).stringCellValue,
                row.getCell(4).stringCellValue,
                row.getCell(5).stringCellValue
            )
            val hobbyType = HobbyType(
                name,
                description,
                imageUrl,
                mbtiType,
                fitHobbyTypes
            )
            em.persist(hobbyType)
        }
    }

}
