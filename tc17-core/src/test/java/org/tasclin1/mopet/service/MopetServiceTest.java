package org.tasclin1.mopet.service;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import org.tasclin1.mopet.domain.Folder;
import org.tasclin1.mopet.domain.Tree;


@Configurable
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:/META-INF/spring/applicationContext*.xml")
@Transactional
public class MopetServiceTest {
	@Autowired
	private MopetService mopetService;
	@Test
	public void testRegime906823() {
		Model model = new ExtendedModelMap();
		Integer idRegime=906823;
		
		mopetService.readRegimeDocT(idRegime, model);
		System.out.println("---"+MopetService.drugDrugM);
		Map<Tree, List<Tree>> drugDrugM = 
				(Map<Tree, List<Tree>>) model.asMap().get(MopetService.drugDrugM);
		System.out.println("---"+MopetService.drugNoticeExprM);
		Map<Tree, List<Tree>> drugNoticeExprM = 
			(Map<Tree, List<Tree>>) model.asMap().get(MopetService.drugNoticeExprM);
		for (Entry<Tree, List<Tree>> entry : drugNoticeExprM.entrySet()) {
//			System.out.println("---"+entry.getKey());
		}
		System.out.println("---"+MopetService.REGIMET);
		Tree regimeT = (Tree) model.asMap().get(MopetService.REGIMET);
		for (Tree tree : regimeT.getChildTs()) {
			System.out.println("---"+tree);
//			System.out.println("---"+drugNoticeExprM.get(tree));
			System.out.println("-drugDrugM--"+drugDrugM.get(tree));
		}
	}
//	@Test
	public void testFolder9930() {
		System.out.println("---");
		System.out.println("---"+mopetService);
		System.out.println("---"+mopetService.getEm());
		Model model = new ExtendedModelMap();
		Integer idFolder=9930;
		Folder readFolderO2doc = mopetService.readFolderO2doc(idFolder, model);
		System.out.println("---"+readFolderO2doc);
		System.out.println("---"+readFolderO2doc.getChildFs());
	}
}
