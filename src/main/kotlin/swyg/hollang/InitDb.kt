package swyg.hollang

import jakarta.annotation.PostConstruct
import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext
import org.apache.poi.ss.usermodel.Workbook
import org.apache.poi.ss.usermodel.WorkbookFactory
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import swyg.hollang.entity.*
import swyg.hollang.repository.test.TestJpaRepository
import java.io.FileNotFoundException
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
class InitService(
    val testJpaRepository: TestJpaRepository
) {

    @PersistenceContext
    private lateinit var em: EntityManager

    private final val INIT_DATA_PATH = "static/initData.xlsx"
    private final val IMG_URL = "https://test.com"

    fun initFile(): Workbook {
        val inputStream: InputStream = Thread.currentThread().contextClassLoader.getResourceAsStream(INIT_DATA_PATH)
            ?: throw FileNotFoundException("Resource file not found: $INIT_DATA_PATH")

        return WorkbookFactory.create(inputStream)
    }

    fun initTestData(testVersion: Int) {
        val workbook = initFile()
        val sheet = workbook.getSheet("test")

        val questions: MutableSet<Question> = mutableSetOf()
        for (rowIndex in 1..sheet.lastRowNum) {
            val row = sheet.getRow(rowIndex)
            if(row.getCell(0) == null) break

            val answers: MutableSet<Answer> = mutableSetOf()
            for (cellIndex in row.firstCellNum + 1..row.firstCellNum + 2) {
                val cell = row.getCell(cellIndex)
                val cellValue = cell?.stringCellValue ?: ""
                val answer = Answer(cellIndex, cellValue)
                answers.add(answer)
            }

            val content = row.getCell(0).stringCellValue
            val question = Question(rowIndex, content, answers)
            questions.add(question)
        }
        //cascade type을 all로 해놨으니 영속성이 전이돼서 부모 엔티티를 영속화시키면 자식 엔티티도 영속화된다.
        testJpaRepository.save(Test(testVersion, questions))
    }

    fun initHobbyData() {
        val workbook = initFile()

        val sheet = workbook.getSheet("hobby")
        for (rowIndex in 1..sheet.lastRowNum) {
            val row = sheet.getRow(rowIndex)
            if(row.getCell(0) == null) break

            val originalName = row.getCell(0).stringCellValue
            val shortName = row.getCell(1).stringCellValue
            val summary = row.getCell(2).stringCellValue
            val description = row.getCell(3).stringCellValue
            val imageName = row.getCell(4).stringCellValue
            val imageUrl = "${IMG_URL}/images/hobby/$imageName.png"
            val hobby = Hobby(originalName, shortName, summary, description, imageUrl)
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
            val fitHobbyTypes = mutableSetOf(
                FitHobbyType(row.getCell(3).stringCellValue, 1),
                FitHobbyType(row.getCell(4).stringCellValue, 2),
                FitHobbyType(row.getCell(5).stringCellValue, 3),
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
