package bo.umss.app.inventorySp.line.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import bo.umss.app.inventorySp.exception.EmptyFieldException;
import bo.umss.app.inventorySp.line.model.Line;
import bo.umss.app.inventorySp.line.repository.LineRepository;
import bo.umss.app.inventorySp.line.service.LineService;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { LineServiceTestConfig.class })
public class LineServiceTest {

	@Autowired
	@Qualifier("lineService")
	private LineService lineService;

	@MockBean(name = "lineRepositoryMocked")
	@Qualifier("lineRepository")
	private LineRepository lineRepository;

	private Line potentialLine;
	private String potentialName;

	@BeforeEach
	public void setUp() {
		potentialName = "calderas";
		potentialLine = Line.at(potentialName);
	}

	@Test
	public void verifyNoFoundName() {
		String potentialName = "calderas";
		assertThrows(RuntimeException.class, () -> lineService.findByName(potentialName));
	}

	@Test
	public void verifyWrongCompareValueName() {
		String potentialNameCompare = "cal";
		Mockito.when(lineRepository.findByName(potentialName)).thenReturn(potentialLine);
		Line line = lineService.findByName(potentialName);

		assertFalse(line.compareOtherName(potentialNameCompare));
	}

	@Test
	public void verifyCorrectCompareValueName() {
		Mockito.when(lineRepository.findByName(potentialName)).thenReturn(potentialLine);
		Line line = lineService.findByName(potentialName);

		assertTrue(line.compareOtherName(potentialName));
	}

	@Test
	public void verifyGetWrongName() {
		String name = "juego de olla";
		assertFalse(lineService.existsByName(name));
	}

	@Test
	public void verifyGetCorectName() {
		String potentialName = "calderas";
		Mockito.when(lineRepository.existsByName(potentialName)).thenReturn(Boolean.TRUE);

		assertTrue(lineService.existsByName(potentialName));
	}

	@Test
	public void verifyListIsEmpty() {
		List<Line> lineList = lineService.findAll();

		assertEquals(0, lineList.size());
	}

	@Test
	public void verifyListFetchOneItem() {
		List<Line> responseList = new ArrayList<>();
		responseList.add(potentialLine);
		Mockito.when(lineRepository.findAll()).thenReturn(responseList);
		List<Line> lineList = lineService.findAll();

		assertEquals(1, lineList.size());
	}

	@Test
	public void verifyAlreadyExistLine() {
		Mockito.when(lineRepository.existsByName(potentialName)).thenReturn(Boolean.TRUE);

		assertThrows(RuntimeException.class, () -> lineService.create(potentialLine));
	}

	@Test
	public void verifyCompareNameIsWrong() {
		Mockito.when(lineRepository.save(potentialLine)).thenReturn(potentialLine);
		Line lineResult = lineService.create(potentialLine);

		assertFalse(lineResult.compareOtherName(potentialName + "t"));
	}

	@Test
	public void verifyCompareNameIsCorrect() {
		Mockito.when(lineRepository.save(potentialLine)).thenReturn(potentialLine);
		Line lineResult = lineService.create(potentialLine);

		assertTrue(lineResult.compareOtherName(potentialName));
	}

	@Test
	public void verifyCompareNameAfterWasUpdate() {
		String newPotentialName = "nuevas calderas";
		potentialLine.setName(newPotentialName);
		Mockito.when(lineRepository.save(potentialLine)).thenReturn(potentialLine);
		Line lineUpdate = lineService.update(potentialLine);

		assertTrue(lineUpdate.compareOtherName(newPotentialName));
	}

	@Test
	public void verifyThrowExceptionEmptyName() {
		assertThrows(EmptyFieldException.class, () -> lineService.findByName(""));
	}
}
