package net.sourceforge.fenixedu.applicationTier.Servico.pedagogicalCouncil.elections;

import net.sourceforge.fenixedu.applicationTier.Service;
import net.sourceforge.fenixedu.applicationTier.Servico.exceptions.FenixServiceException;
import net.sourceforge.fenixedu.dataTransferObject.pedagogicalCouncil.elections.ElectionPeriodBean;
import net.sourceforge.fenixedu.domain.Degree;
import net.sourceforge.fenixedu.domain.ExecutionYear;
import net.sourceforge.fenixedu.domain.elections.YearDelegateElection;
import net.sourceforge.fenixedu.domain.exceptions.DomainException;
import net.sourceforge.fenixedu.persistenceTier.ExcepcaoPersistencia;

public class CreateDelegateCandidacyPeriod extends Service {

	public void run(ElectionPeriodBean bean) throws FenixServiceException, ExcepcaoPersistencia {
		final Integer degreeOID = bean.getDegree().getIdInternal();
		
		this.run(bean, degreeOID.toString());
	}
	
	public void run(ElectionPeriodBean bean, String degreeOID) throws FenixServiceException, ExcepcaoPersistencia {
		final ExecutionYear executionYear = ExecutionYear.readCurrentExecutionYear();
		final Degree degree = rootDomainObject.readDegreeByOID(Integer.parseInt(degreeOID));
		
		try {
			YearDelegateElection.createDelegateElectionWithCandidacyPeriod(degree, executionYear, 
					bean.getStartDate(), bean.getEndDate(), bean.getCurricularYear());
			
		} catch (DomainException ex) {
			throw new FenixServiceException (ex.getMessage(), ex.getArgs());
		}
	}
}
