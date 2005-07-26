package net.sourceforge.fenixedu.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.sourceforge.fenixedu.domain.curriculum.EnrollmentCondition;
import net.sourceforge.fenixedu.domain.curriculum.EnrollmentState;
import net.sourceforge.fenixedu.domain.curriculum.EnrolmentEvaluationType;
import net.sourceforge.fenixedu.domain.exceptions.DomainException;
import net.sourceforge.fenixedu.util.EnrolmentEvaluationState;

public class EnrolmentTest extends DomainTestBase {

	// A + B -> C
	// A -> D
	
	private IEnrolment enrolmentA;
	private IEnrolment enrolmentB;
	private IEnrolment enrolmentC;
	private IEnrolment enrolmentD;
	private IEnrolment enrolmentE;		//enrolment able to unenroll
	private IEnrolment enrolmentF;		//enrolment unable to unenroll
	private IEnrolment enrolmentWithImprovement;
	private IEnrolment enrolmentWithoutImprovement;
	private List<IEnrolmentEvaluation> evaluations;
	private List<ICreditsInAnySecundaryArea> creditsInAnySecundaryAreas;
	private List<ICreditsInScientificArea> creditsInScientificAreas;
	private List<IEquivalentEnrolmentForEnrolmentEquivalence> equivalentEnrolmentsA;
	private List<IEquivalentEnrolmentForEnrolmentEquivalence> equivalentEnrolmentsB;
	private List<IEnrolmentEquivalence> enrolmentEquivalencesC;
	private List<IEnrolmentEquivalence> enrolmentEquivalencesD;
	private IEnrolmentEvaluation improvementEvaluation;
	
	private IEnrolment enrolmentToInitialize = null;
	private IStudentCurricularPlan studentCurricularPlan = null;
	private ICurricularCourse curricularCourseToEnroll = null;
	private IExecutionCourse executionCourseToEnroll = null;
	private IAttends attendsToEnroll = null;
	private IExecutionPeriod currentExecutionPeriod = null;
	private EnrollmentCondition enrolmentCondition = null;
	private String createdBy = null;
	private IStudent thisStudent = null;
	
	private IEnrolment enrolmentToInitializeForAnotherExecutionPeriod = null;
	private IExecutionCourse executionCourseToEnrollWithAttendsForThisStudent = null;
	private IExecutionPeriod anotherExecutionPeriod = null;

	private IEnrolment enrolmentToReadFrom = null;
	private IEnrolmentEvaluation evaluationWithGradeToBeRead = null;
	private IEnrolmentEvaluation evaluationWithoutGradeToBeRead = null;
	private String gradeToSearchFor = null;
	private String impossibleGrade = null;
	private EnrolmentEvaluationType enrolmentEvaluationTypeToSearchFor = null;
	
	private IEnrolment enrolmentToSubmitWithoutTemporaryEvaluation = null;
	private IEnrolment enrolmentToSubmitWithTemporaryEvaluation = null;
	private IEnrolmentEvaluation existingTemporaryEnrolmentEvaluation = null;
	private EnrolmentEvaluationType notExistingEnrolmentEvaluationType = null;
	private EnrolmentEvaluationType existingEnrolmentEvaluationType = null;
	private IMark realMark = null;
	private IMark emptyMark = null;
	private Date examDate = null;
	private IEmployee employeeSubmittingGrade = null;
	private IPerson personResponsibleForGrade = null;
	private String observation = null;
	
	private IEnrolment improvementEnrolment = null;
	private IEnrolment nonImprovementEnrolment = null;
	private IExecutionCourse executionCourseForImprovement = null;
	
	private IEnrolment enrolmentToUnEnrollImprovement = null;
	private IEnrolmentEvaluation improvementEnrolmentEvaluation = null;
	private IEnrolmentEvaluation nonImprovementEnrolmentEvaluation = null;
	private IExecutionPeriod executionPeriodToUnEnrollImprovement = null;
	private IAttends attendsToDelete = null;
	private IExecutionCourse executionCourseToUnEnrollImprovement = null;
	
	private IEnrolment enrolmentToImprove = null;
	private ICurricularCourse curricularCourseToImprove = null;
	private IExecutionCourse executionCourseToEnrollImprovement = null;
	private IStudent studentToImprove = null;
	private IExecutionPeriod executionPeriodForImprovement = null;
	private IEmployee someEmployee = null;
	
	protected void setUp() throws Exception {
        super.setUp();
		
		setUpForDeleteCase();
		setUpForUnEnrollCase();
		setUpForGetImprovmentEvaluation();
		setUpForGetEnrolmentEvaluationByEnrolmentEvaluationTypeAndGradeCase();
		setUpForInitializeAsNewCase();
		setUpForSubmitEnrolmentEvaluationCase();
		setUpForIsImprovementForExecutionCourseCase();
		
		setUpForUnEnrollImprovementCase();
		setUpCreateEnrolmentEvaluationForImprovement();
    }
	
	private void setUpCreateEnrolmentEvaluationForImprovement() {
		enrolmentToImprove = new Enrolment();
		curricularCourseToImprove = new CurricularCourse();
		executionCourseToEnrollImprovement = new ExecutionCourse();
		studentToImprove = new Student();
		executionPeriodForImprovement = new ExecutionPeriod();
		someEmployee = new Employee();
		
		executionCourseToEnrollImprovement.setExecutionPeriod(executionPeriodForImprovement);
		curricularCourseToImprove.addAssociatedExecutionCourses(executionCourseToEnrollImprovement);
		enrolmentToImprove.setCurricularCourse(curricularCourseToImprove);
	}

	private void setUpForUnEnrollImprovementCase() {

		enrolmentToUnEnrollImprovement = new Enrolment();
		
		improvementEnrolmentEvaluation = new EnrolmentEvaluation();
		improvementEnrolmentEvaluation.setEnrolmentEvaluationType(EnrolmentEvaluationType.IMPROVEMENT);
		improvementEnrolmentEvaluation.setEnrolmentEvaluationState(EnrolmentEvaluationState.TEMPORARY_OBJ);
		
		nonImprovementEnrolmentEvaluation = new EnrolmentEvaluation();
		nonImprovementEnrolmentEvaluation.setEnrolmentEvaluationType(EnrolmentEvaluationType.NORMAL);
		nonImprovementEnrolmentEvaluation.setEnrolmentEvaluationState(EnrolmentEvaluationState.TEMPORARY_OBJ);
		
		executionPeriodToUnEnrollImprovement = new ExecutionPeriod();
		attendsToDelete = new Attends();
		executionCourseToUnEnrollImprovement = new ExecutionCourse();
		
		IStudentCurricularPlan scp = new StudentCurricularPlan();
		IStudent student = new Student();
		IStudent otherStudent = new Student();
		ICurricularCourse cc = new CurricularCourse();
		IExecutionCourse ec1 = new ExecutionCourse();
		IExecutionPeriod ep1 = new ExecutionPeriod();
		IAttends otherAttends = new Attends();

		enrolmentToUnEnrollImprovement.addEvaluations(improvementEnrolmentEvaluation);
		enrolmentToUnEnrollImprovement.addEvaluations(nonImprovementEnrolmentEvaluation);
		
		scp.setStudent(student);
		attendsToDelete.setAluno(student);
		attendsToDelete.setDisciplinaExecucao(executionCourseToUnEnrollImprovement);
		otherAttends.setAluno(otherStudent);
		otherAttends.setDisciplinaExecucao(executionCourseToUnEnrollImprovement);
		executionCourseToUnEnrollImprovement.setExecutionPeriod(executionPeriodToUnEnrollImprovement);
		ec1.setExecutionPeriod(ep1);
		
		cc.addAssociatedExecutionCourses(executionCourseToUnEnrollImprovement);
		cc.addAssociatedExecutionCourses(ec1);
		enrolmentToUnEnrollImprovement.setCurricularCourse(cc);
		enrolmentToUnEnrollImprovement.setStudentCurricularPlan(scp);
	}

	private void setUpForDeleteCase() {
		
		enrolmentA = new Enrolment();
		enrolmentB = new Enrolment();
		enrolmentC = new Enrolment();
		enrolmentD = new Enrolment();
				
		/*
		 *  EnrolmentEvaluation
		 */
		IEnrolmentEvaluation ee1 = new EnrolmentEvaluation();
		IEnrolmentEvaluation ee2 = new EnrolmentEvaluation();
		
		IPerson person = new Person();
		IEmployee employee = new Employee();
		
		ee1.setPersonResponsibleForGrade(person);
		ee2.setPersonResponsibleForGrade(person);
		ee1.setEmployee(employee);
		ee2.setEmployee(employee);
		
		evaluations = new ArrayList<IEnrolmentEvaluation>();
		evaluations.add(ee1);
		evaluations.add(ee2);
		enrolmentA.addEvaluations(ee1);
		enrolmentA.addEvaluations(ee2);
		
		
		
		/*
		 * ExecutionPeriod
		 */
		IExecutionPeriod ep1 = new ExecutionPeriod();
		enrolmentA.setExecutionPeriod(ep1);
		
		
		/*
		 * StudentCurricularPlan
		 */
		IStudentCurricularPlan scp = new StudentCurricularPlan();
		enrolmentA.setStudentCurricularPlan(scp);
		
		
		/*
		 * CreditsInAnySecundaryArea
		 */
		ICreditsInAnySecundaryArea ciasa1 = new CreditsInAnySecundaryArea();
		ICreditsInAnySecundaryArea ciasa2 = new CreditsInAnySecundaryArea();
		
		ciasa1.setStudentCurricularPlan(scp);
		ciasa2.setStudentCurricularPlan(scp);
		
		creditsInAnySecundaryAreas = new ArrayList<ICreditsInAnySecundaryArea>();
		creditsInAnySecundaryAreas.add(ciasa1);
		creditsInAnySecundaryAreas.add(ciasa2);
		enrolmentA.addCreditsInAnySecundaryAreas(ciasa1);
		enrolmentA.addCreditsInAnySecundaryAreas(ciasa2);
		
		
		/*
		 * CreditsInScientificArea
		 */
		ICreditsInScientificArea cisa1 = new CreditsInScientificArea();
		ICreditsInScientificArea cisa2 = new CreditsInScientificArea();		
		cisa1.setStudentCurricularPlan(scp);
		cisa2.setStudentCurricularPlan(scp);
		
		creditsInScientificAreas = new ArrayList<ICreditsInScientificArea>();
		creditsInScientificAreas.add(cisa1);
		creditsInScientificAreas.add(cisa2);
		enrolmentA.addCreditsInScientificAreas(cisa1);
		enrolmentA.addCreditsInScientificAreas(cisa2);
		
		
		/*
		 * CurricularCourse
		 */
		ICurricularCourse cc = new CurricularCourse();
		enrolmentA.setCurricularCourse(cc);
		
			
		IEquivalentEnrolmentForEnrolmentEquivalence eeee1 = new EquivalentEnrolmentForEnrolmentEquivalence();
		IEquivalentEnrolmentForEnrolmentEquivalence eeee2 = new EquivalentEnrolmentForEnrolmentEquivalence();
		IEquivalentEnrolmentForEnrolmentEquivalence eeee3 = new EquivalentEnrolmentForEnrolmentEquivalence();
		IEnrolmentEquivalence eeq1 = new EnrolmentEquivalence();
		IEnrolmentEquivalence eeq2 = new EnrolmentEquivalence();
		
		equivalentEnrolmentsA = new ArrayList<IEquivalentEnrolmentForEnrolmentEquivalence>();
		equivalentEnrolmentsB = new ArrayList<IEquivalentEnrolmentForEnrolmentEquivalence>();
		equivalentEnrolmentsA.add(eeee1);
		equivalentEnrolmentsB.add(eeee2);
		equivalentEnrolmentsA.add(eeee3);
		enrolmentA.addEquivalentEnrolmentForEnrolmentEquivalences(eeee1);
		enrolmentB.addEquivalentEnrolmentForEnrolmentEquivalences(eeee2);
		enrolmentA.addEquivalentEnrolmentForEnrolmentEquivalences(eeee3);
		
		eeee1.setEnrolmentEquivalence(eeq1);
		eeee2.setEnrolmentEquivalence(eeq1);
		eeee3.setEnrolmentEquivalence(eeq2);
		eeq1.setEnrolment(enrolmentC);
		eeq2.setEnrolment(enrolmentD);
		
		enrolmentEquivalencesC = new ArrayList<IEnrolmentEquivalence>();
		enrolmentEquivalencesD = new ArrayList<IEnrolmentEquivalence>();
		enrolmentEquivalencesC.add(eeq1);
		enrolmentEquivalencesD.add(eeq2);
	}
	
	
	private void setUpForUnEnrollCase() {
		
		enrolmentE = new Enrolment();
		enrolmentF = new Enrolment();
		
		IEnrolmentEvaluation ee3 = new EnrolmentEvaluation();
		IEnrolmentEvaluation ee4 = new EnrolmentEvaluation();
		
		ee3.setEnrolmentEvaluationState(EnrolmentEvaluationState.TEMPORARY_OBJ);
		ee3.setEnrolmentEvaluationType(EnrolmentEvaluationType.NORMAL);
		
		ee4.setEnrolmentEvaluationState(EnrolmentEvaluationState.FINAL_OBJ);
		ee4.setEnrolmentEvaluationType(EnrolmentEvaluationType.NORMAL);
		ee4.setGrade("20");
		
		enrolmentE.addEvaluations(ee3);
		enrolmentF.addEvaluations(ee4);
	}
	
	
	private void setUpForGetImprovmentEvaluation() {
		
		enrolmentWithImprovement = new Enrolment();
		enrolmentWithoutImprovement = new Enrolment();
		
		IEnrolmentEvaluation normalEvaluation = new EnrolmentEvaluation();
		IEnrolmentEvaluation normalEvaluationToImprove = new EnrolmentEvaluation();
		improvementEvaluation = new EnrolmentEvaluation();
		
		normalEvaluation.setEnrolmentEvaluationState(EnrolmentEvaluationState.TEMPORARY_OBJ);
		normalEvaluation.setEnrolmentEvaluationType(EnrolmentEvaluationType.NORMAL);
		
		normalEvaluationToImprove.setEnrolmentEvaluationState(EnrolmentEvaluationState.FINAL_OBJ);
		normalEvaluationToImprove.setEnrolmentEvaluationType(EnrolmentEvaluationType.NORMAL);
		
		improvementEvaluation.setEnrolmentEvaluationState(EnrolmentEvaluationState.TEMPORARY_OBJ);
		improvementEvaluation.setEnrolmentEvaluationType(EnrolmentEvaluationType.IMPROVEMENT);
		
		normalEvaluation.setEnrolment(enrolmentWithoutImprovement);
		normalEvaluationToImprove.setEnrolment(enrolmentWithImprovement);
		improvementEvaluation.setEnrolment(enrolmentWithImprovement);
	}
	

    private void setUpForIsImprovementForExecutionCourseCase() {
		improvementEnrolment = new Enrolment();
		nonImprovementEnrolment = new Enrolment();
		
		IExecutionPeriod sameExecutionPeriod = new ExecutionPeriod();
		executionCourseForImprovement = new ExecutionCourse();
		executionCourseForImprovement.setExecutionPeriod(sameExecutionPeriod);
		nonImprovementEnrolment.setExecutionPeriod(sameExecutionPeriod);
		
		IExecutionPeriod otherExecutionPeriod = new ExecutionPeriod();
		improvementEnrolment.setExecutionPeriod(otherExecutionPeriod);
	}

	private void setUpForSubmitEnrolmentEvaluationCase() {

		enrolmentToSubmitWithoutTemporaryEvaluation = new Enrolment();
		enrolmentToSubmitWithTemporaryEvaluation = new Enrolment();
		
		existingTemporaryEnrolmentEvaluation = new EnrolmentEvaluation();

		notExistingEnrolmentEvaluationType = EnrolmentEvaluationType.CLOSED;
		existingEnrolmentEvaluationType = EnrolmentEvaluationType.NORMAL;
		existingTemporaryEnrolmentEvaluation.setEnrolmentEvaluationType(existingEnrolmentEvaluationType);
		existingTemporaryEnrolmentEvaluation.setEnrolmentEvaluationState(EnrolmentEvaluationState.TEMPORARY_OBJ);
		enrolmentToSubmitWithTemporaryEvaluation.addEvaluations(existingTemporaryEnrolmentEvaluation);
		
		realMark = new Mark();
		realMark.setMark("20");
		
		emptyMark = new Mark();
		emptyMark.setMark("");
		
		examDate = new Date(2001,4,15);
		employeeSubmittingGrade = new Employee();
		personResponsibleForGrade = new Person();
		observation = "submission";
	}

	private void setUpForInitializeAsNewCase() {
		enrolmentToInitialize = new Enrolment();
		
		thisStudent = new Student();
		IStudent otherStudent = new Student();
		
		IAttends attends2 = new Attends();
		attends2.setAluno(otherStudent);

		
		studentCurricularPlan = new StudentCurricularPlan();
		studentCurricularPlan.setStudent(thisStudent);
		curricularCourseToEnroll = new CurricularCourse();
		currentExecutionPeriod = new ExecutionPeriod();
		IExecutionPeriod otherExecutionPeriod = new ExecutionPeriod();
		
		enrolmentCondition = EnrollmentCondition.FINAL;
		createdBy = "XxX";
		
		IExecutionCourse ec1 = new ExecutionCourse();
		ec1.setExecutionPeriod(otherExecutionPeriod);
		curricularCourseToEnroll.addAssociatedExecutionCourses(ec1);
		
		executionCourseToEnroll = new ExecutionCourse();
		executionCourseToEnroll.setExecutionPeriod(currentExecutionPeriod);		
		executionCourseToEnroll.addAttends(attends2);
		curricularCourseToEnroll.addAssociatedExecutionCourses(executionCourseToEnroll);

		attendsToEnroll = new Attends();
		attendsToEnroll.setAluno(thisStudent);
		enrolmentToInitializeForAnotherExecutionPeriod = new Enrolment();
		anotherExecutionPeriod = new ExecutionPeriod();
		
		executionCourseToEnrollWithAttendsForThisStudent = new ExecutionCourse();
		executionCourseToEnrollWithAttendsForThisStudent.setExecutionPeriod(anotherExecutionPeriod);
		executionCourseToEnrollWithAttendsForThisStudent.addAttends(attendsToEnroll);
		curricularCourseToEnroll.addAssociatedExecutionCourses(executionCourseToEnrollWithAttendsForThisStudent);
	}

	private void setUpForGetEnrolmentEvaluationByEnrolmentEvaluationTypeAndGradeCase() {
		enrolmentToReadFrom = new Enrolment();
		gradeToSearchFor = "20";
		impossibleGrade = "300";
		enrolmentEvaluationTypeToSearchFor = EnrolmentEvaluationType.EQUIVALENCE;
		String otherGrade = "10";
		EnrolmentEvaluationType otherType = EnrolmentEvaluationType.CLOSED;
		
		// with required type and grade
		IEnrolmentEvaluation ee1 = createEnrolmentEvaluation(enrolmentToReadFrom,enrolmentEvaluationTypeToSearchFor,gradeToSearchFor);
		evaluationWithGradeToBeRead = ee1;
		
		// with required type and NOT grade
		createEnrolmentEvaluation(enrolmentToReadFrom,enrolmentEvaluationTypeToSearchFor,otherGrade);
		// without required type and with grade
		createEnrolmentEvaluation(enrolmentToReadFrom,otherType,gradeToSearchFor);
		// without required type or grade
		createEnrolmentEvaluation(enrolmentToReadFrom,otherType,otherGrade);
		
		// with required type and null grade
		IEnrolmentEvaluation ee2 = createEnrolmentEvaluation(enrolmentToReadFrom,enrolmentEvaluationTypeToSearchFor,null);
		evaluationWithoutGradeToBeRead = ee2;
	}

	protected void tearDown() throws Exception {
        super.tearDown();
    }
	
	public void testDelete() {
		
		enrolmentD.delete();
				
		assertFalse(enrolmentD.hasAnyEnrolmentEquivalences());
		
		for (IEnrolmentEquivalence equivalence : enrolmentEquivalencesD) {
			assertFalse(equivalence.hasAnyEquivalenceRestrictions());
			assertFalse(equivalence.hasEnrolment());
		}
		
		assertTrue(enrolmentA.hasAnyEvaluations());
		assertTrue(enrolmentA.hasExecutionPeriod());
		assertTrue(enrolmentA.hasStudentCurricularPlan());
		assertTrue(enrolmentA.hasAnyCreditsInAnySecundaryAreas());
		assertTrue(enrolmentA.hasAnyCreditsInScientificAreas());
		assertTrue(enrolmentA.hasCurricularCourse());
		assertFalse(enrolmentA.hasAnyEnrolmentEquivalences());
		assertTrue(enrolmentA.hasAnyEquivalentEnrolmentForEnrolmentEquivalences());
		
		enrolmentA.delete();
		
		assertFalse(enrolmentA.hasAnyEvaluations());
		assertFalse(enrolmentA.hasExecutionPeriod());
		assertFalse(enrolmentA.hasStudentCurricularPlan());
		assertFalse(enrolmentA.hasAnyCreditsInAnySecundaryAreas());
		assertFalse(enrolmentA.hasAnyCreditsInScientificAreas());
		assertFalse(enrolmentA.hasCurricularCourse());
		assertFalse(enrolmentA.hasAnyEnrolmentEquivalences());
		assertFalse(enrolmentA.hasAnyEquivalentEnrolmentForEnrolmentEquivalences());
		
		for (IEnrolmentEvaluation eval : evaluations) {
			assertFalse(eval.hasPersonResponsibleForGrade());
			assertFalse(eval.hasEmployee());
		}
		
		for (ICreditsInAnySecundaryArea credits : creditsInAnySecundaryAreas)
			assertFalse(credits.hasStudentCurricularPlan());
		
		for (ICreditsInScientificArea credits : creditsInScientificAreas)
			assertFalse(credits.hasStudentCurricularPlan());
		
		assertFalse(enrolmentC.hasAnyEnrolmentEquivalences());
		assertFalse(enrolmentC.hasAnyEquivalentEnrolmentForEnrolmentEquivalences());
		
		for (IEquivalentEnrolmentForEnrolmentEquivalence equivalentEnrolment : equivalentEnrolmentsA) {
			assertFalse(equivalentEnrolment.hasEquivalentEnrolment());
			assertFalse(equivalentEnrolment.hasEnrolmentEquivalence());
		}
		
		for (IEnrolmentEquivalence equivalence : enrolmentEquivalencesC) {
			assertFalse(equivalence.hasEnrolment());
			assertFalse(equivalence.hasAnyEquivalenceRestrictions());
		}
	}
	
	
	
	public void testUnEnroll() {
		try {
			enrolmentE.unEnroll();
			
		} catch (DomainException e) {
			fail("Should have been deleted.");
		}
		
		
		try {
			enrolmentF.unEnroll();
			fail("Should not have been deleted.");
		} catch (DomainException e) {
			
		}
	}
	
	
	public void testGetImprovementEvaluation() {
		
		assertNull(enrolmentWithoutImprovement.getImprovementEvaluation());
		assertTrue(enrolmentWithImprovement.getImprovementEvaluation().equals(improvementEvaluation));	
	}
	
	
	public void testGetEnrolmentEvaluationByEnrolmentEvaluationTypeAndGrade() {
		IEnrolmentEvaluation enrolmentEvaluationWithGrade = enrolmentToReadFrom.getEnrolmentEvaluationByEnrolmentEvaluationTypeAndGrade(enrolmentEvaluationTypeToSearchFor,gradeToSearchFor);
		assertEquals(enrolmentEvaluationWithGrade,evaluationWithGradeToBeRead);
		
		IEnrolmentEvaluation enrolmentEvaluationWithoutGrade = enrolmentToReadFrom.getEnrolmentEvaluationByEnrolmentEvaluationTypeAndGrade(enrolmentEvaluationTypeToSearchFor,null);
		assertEquals(enrolmentEvaluationWithoutGrade,evaluationWithoutGradeToBeRead);
		
		IEnrolmentEvaluation nullEnrolmentEvaluation = enrolmentToReadFrom.getEnrolmentEvaluationByEnrolmentEvaluationTypeAndGrade(enrolmentEvaluationTypeToSearchFor,impossibleGrade);
		assertNull(nullEnrolmentEvaluation);
	}
	
	public void testInitializeAsNew() {
		Date before = new Date();
		enrolmentToInitialize.initializeAsNew(studentCurricularPlan,curricularCourseToEnroll,currentExecutionPeriod,enrolmentCondition,createdBy);
		Date after = new Date();
		
		assertEquals(enrolmentToInitialize.getStudentCurricularPlan(),studentCurricularPlan);
		assertEquals(enrolmentToInitialize.getCurricularCourse(),curricularCourseToEnroll);
		assertEquals(enrolmentToInitialize.getExecutionPeriod(),currentExecutionPeriod);
		assertEquals(enrolmentToInitialize.getCondition(),enrolmentCondition);
		assertEquals(enrolmentToInitialize.getCreatedBy(),createdBy);
		assertEquals(enrolmentToInitialize.getEnrollmentState(),EnrollmentState.ENROLLED);
		assertEquals(enrolmentToInitialize.getEnrolmentEvaluationType(),EnrolmentEvaluationType.NORMAL);
		assertTrue(before.before(enrolmentToInitialize.getCreationDate()) && after.after(enrolmentToInitialize.getCreationDate()));
		
		assertTrue(enrolmentToInitialize.getEvaluationsCount() == 1);
		IEnrolmentEvaluation evaluation = enrolmentToInitialize.getEvaluations().get(0);
		assertEquals(evaluation.getEnrolmentEvaluationState(),EnrolmentEvaluationState.TEMPORARY_OBJ);
        assertEquals(evaluation.getEnrolmentEvaluationType(),EnrolmentEvaluationType.NORMAL);
		assertNull(evaluation.getGrade());
		
		assertTrue(enrolmentToInitialize.getAttendsCount() == 1);
		IAttends attends = enrolmentToInitialize.getAttends().get(0);
		assertEquals(attends.getAluno(),studentCurricularPlan.getStudent());
		assertEquals(attends.getDisciplinaExecucao(),executionCourseToEnroll);
		assertEquals(attends.getEnrolment(),enrolmentToInitialize);
		
		// only difference lies in the Attends assignment part
		enrolmentToInitializeForAnotherExecutionPeriod.initializeAsNew(studentCurricularPlan,curricularCourseToEnroll,anotherExecutionPeriod,enrolmentCondition,createdBy);
		assertTrue(enrolmentToInitializeForAnotherExecutionPeriod.getAttendsCount() == 1);
		IAttends att = enrolmentToInitializeForAnotherExecutionPeriod.getAttends().get(0);
		assertEquals(att,attendsToEnroll);
		assertEquals(att.getAluno(),thisStudent);
		assertEquals(att.getDisciplinaExecucao(),executionCourseToEnrollWithAttendsForThisStudent);
		assertEquals(att.getEnrolment(),enrolmentToInitializeForAnotherExecutionPeriod);
		
	}

	public void testSubmitEnrolmentEvaluation() {

		long sleepTime = 1000;
		
		// there isn't an evaluation with TEMPORARY state
		Date before = new Date();
		sleep(sleepTime);
		IEnrolmentEvaluation newEvaluation = enrolmentToSubmitWithoutTemporaryEvaluation.submitEnrolmentEvaluation(
				notExistingEnrolmentEvaluationType,realMark,employeeSubmittingGrade,
				personResponsibleForGrade,examDate,observation);
		
		sleep(sleepTime);
		Date after = new Date();

		assertTrue(enrolmentToSubmitWithoutTemporaryEvaluation.getEvaluations().contains(newEvaluation));
		assertEquals(newEvaluation.getGrade().toUpperCase(),realMark.getMark().toUpperCase());
		assertEquals(newEvaluation.getEnrolmentEvaluationType(),notExistingEnrolmentEvaluationType);
		assertEquals(newEvaluation.getEnrolmentEvaluationState(),EnrolmentEvaluationState.TEMPORARY_OBJ);
		assertEquals(newEvaluation.getObservation(),observation);
		assertEquals(newEvaluation.getPersonResponsibleForGrade(),personResponsibleForGrade);
		assertEquals(newEvaluation.getEmployee(),employeeSubmittingGrade);
		assertEquals(newEvaluation.getExamDate(),examDate);
		assertTrue(before.before(newEvaluation.getWhen()));
		assertTrue(after.after(newEvaluation.getWhen()));
		assertTrue(before.before(newEvaluation.getGradeAvailableDate()));
		assertTrue(after.after(newEvaluation.getGradeAvailableDate()));

		// there *is* an evaluation with TEMPORARY state
		IEnrolmentEvaluation existingEvaluation = enrolmentToSubmitWithTemporaryEvaluation.submitEnrolmentEvaluation(
				existingEnrolmentEvaluationType,realMark,employeeSubmittingGrade,
				personResponsibleForGrade,examDate,observation);
		
		assertTrue(enrolmentToSubmitWithTemporaryEvaluation.getEvaluations().contains(existingEvaluation));
		assertEquals(existingEvaluation,existingTemporaryEnrolmentEvaluation);
		
		// null mark
		IEnrolmentEvaluation anotherEnrolmentEvaluation = enrolmentToSubmitWithoutTemporaryEvaluation.submitEnrolmentEvaluation(
				notExistingEnrolmentEvaluationType,null,employeeSubmittingGrade,
				personResponsibleForGrade,examDate,observation);
		
		assertTrue(anotherEnrolmentEvaluation.getGrade().equals("NA"));

		// "" mark
		anotherEnrolmentEvaluation = enrolmentToSubmitWithoutTemporaryEvaluation.submitEnrolmentEvaluation(
				notExistingEnrolmentEvaluationType,emptyMark,employeeSubmittingGrade,
				personResponsibleForGrade,examDate,observation);
		
		assertTrue(anotherEnrolmentEvaluation.getGrade().equals("NA"));
		
		// null examDate
		before = new Date();
		sleep(sleepTime);
		anotherEnrolmentEvaluation = enrolmentToSubmitWithoutTemporaryEvaluation.submitEnrolmentEvaluation(
				notExistingEnrolmentEvaluationType,realMark,employeeSubmittingGrade,
				personResponsibleForGrade,null,observation);
		sleep(sleepTime);
		after = new Date();
		
		assertTrue(before.before(anotherEnrolmentEvaluation.getExamDate()));
		assertTrue(after.after(anotherEnrolmentEvaluation.getExamDate()));
	}
	
	public void testIsImprovementForExecutionCourse() {
		assertTrue(improvementEnrolment.isImprovementForExecutionCourse(executionCourseForImprovement));
		assertFalse(nonImprovementEnrolment.isImprovementForExecutionCourse(executionCourseForImprovement));
	}
	
	public void testUnEnrollImprovement() {
		
		try {
			enrolmentToUnEnrollImprovement.unEnrollImprovement(executionPeriodToUnEnrollImprovement);
		} catch (DomainException e) {
			fail("Should have unenrolled");
		}
		
		assertFalse(improvementEnrolmentEvaluation.hasEnrolment());
		assertFalse(executionCourseToUnEnrollImprovement.hasAttends(attendsToDelete));
		assertFalse(attendsToDelete.hasAluno());
		
		try {
			enrolmentToUnEnrollImprovement.unEnrollImprovement(executionPeriodToUnEnrollImprovement);
			fail("Should not have unenrolled");
		} catch (DomainException e) {
			
		}
	}
	
	public void testCreateEnrolmentEvaluationForImprovement() {
		assertNull(enrolmentToImprove.getImprovementEvaluation());
		assertFalse(executionCourseToEnrollImprovement.hasAnyAttends());
		
		enrolmentToImprove.createEnrolmentEvaluationForImprovement(someEmployee,executionPeriodForImprovement,studentToImprove);
		
		IEnrolmentEvaluation improvementEvaluation = enrolmentToImprove.getImprovementEvaluation(); 
		assertNotNull(improvementEvaluation);
		assertEquals(improvementEvaluation.getEmployee(),someEmployee);
		assertTrue(executionCourseToEnrollImprovement.hasAnyAttends());
	}
	
	private IEnrolmentEvaluation createEnrolmentEvaluation(IEnrolment enrolment, EnrolmentEvaluationType type, String grade) {
		IEnrolmentEvaluation ee = new EnrolmentEvaluation();
		ee.setEnrolmentEvaluationType(type);
		ee.setGrade(grade);
		ee.setEnrolment(enrolment);
		return ee;
	}
}
