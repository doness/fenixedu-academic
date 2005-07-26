/*
 * Created on 23/Jan/2004
 *  
 */

package net.sourceforge.fenixedu.applicationTier.Servico.grant.contract;

import java.lang.reflect.Proxy;

import net.sourceforge.fenixedu.applicationTier.Servico.exceptions.FenixServiceException;
import net.sourceforge.fenixedu.applicationTier.Servico.exceptions.grant.GrantOrientationTeacherNotFoundException;
import net.sourceforge.fenixedu.applicationTier.Servico.exceptions.grant.InvalidGrantPaymentEntityException;
import net.sourceforge.fenixedu.applicationTier.Servico.framework.EditDomainObjectService;
import net.sourceforge.fenixedu.dataTransferObject.InfoObject;
import net.sourceforge.fenixedu.dataTransferObject.grant.contract.InfoGrantCostCenter;
import net.sourceforge.fenixedu.dataTransferObject.grant.contract.InfoGrantProject;
import net.sourceforge.fenixedu.domain.DomainFactory;
import net.sourceforge.fenixedu.domain.IDomainObject;
import net.sourceforge.fenixedu.domain.ITeacher;
import net.sourceforge.fenixedu.domain.grant.contract.GrantPaymentEntity;
import net.sourceforge.fenixedu.domain.grant.contract.IGrantCostCenter;
import net.sourceforge.fenixedu.domain.grant.contract.IGrantPaymentEntity;
import net.sourceforge.fenixedu.domain.grant.contract.IGrantProject;
import net.sourceforge.fenixedu.persistenceTier.ExcepcaoPersistencia;
import net.sourceforge.fenixedu.persistenceTier.IPersistentObject;
import net.sourceforge.fenixedu.persistenceTier.IPersistentTeacher;
import net.sourceforge.fenixedu.persistenceTier.ISuportePersistente;
import net.sourceforge.fenixedu.persistenceTier.grant.IPersistentGrantCostCenter;

import org.apache.ojb.broker.core.proxy.ProxyHelper;

import pt.utl.ist.berserk.logic.serviceManager.IService;

/**
 * @author Barbosa
 * @author Pica
 */
public class EditGrantPaymentEntity extends EditDomainObjectService implements IService {

    @Override
    protected void copyInformationFromInfoToDomain(ISuportePersistente sp, InfoObject infoObject,
            IDomainObject domainObject) throws ExcepcaoPersistencia, FenixServiceException {
        IGrantPaymentEntity grantPaymentEntity = (IGrantPaymentEntity) domainObject;
        if (grantPaymentEntity instanceof Proxy) {
            grantPaymentEntity = (IGrantPaymentEntity) ProxyHelper.getRealObject(grantPaymentEntity);
        }
        if (grantPaymentEntity instanceof IGrantProject) {
            InfoGrantProject infoGrantProject = (InfoGrantProject) infoObject;

            IGrantProject grantProject = (IGrantProject) grantPaymentEntity;
            grantProject.setNumber(infoGrantProject.getNumber());
            grantProject.setDesignation(infoGrantProject.getDesignation());
            if (infoGrantProject.getInfoResponsibleTeacher() != null) {
                IPersistentTeacher persistentTeacher = sp.getIPersistentTeacher();
                ITeacher teacher = persistentTeacher.readByNumber(infoGrantProject
                        .getInfoResponsibleTeacher().getTeacherNumber());
                if (teacher == null)
                    throw new GrantOrientationTeacherNotFoundException();
                grantProject.setResponsibleTeacher(teacher);
            }
            if (infoGrantProject.getInfoGrantCostCenter() != null) {
                IPersistentGrantCostCenter persistentGrantCostCenter = sp
                        .getIPersistentGrantCostCenter();
                IGrantCostCenter grantCostCenter = persistentGrantCostCenter
                        .readGrantCostCenterByNumber(infoGrantProject.getInfoGrantCostCenter()
                                .getNumber());
                if (grantCostCenter == null)
                    throw new InvalidGrantPaymentEntityException();
                grantProject.setGrantCostCenter(grantCostCenter);
            }

        } else if (grantPaymentEntity instanceof IGrantCostCenter) {
            InfoGrantCostCenter infoGrantCostCenter = (InfoGrantCostCenter) infoObject;

            IGrantCostCenter grantCostCenter = (IGrantCostCenter) grantPaymentEntity;
            grantCostCenter.setNumber(infoGrantCostCenter.getNumber());
            grantCostCenter.setDesignation(infoGrantCostCenter.getDesignation());

            if (infoGrantCostCenter.getInfoResponsibleTeacher() != null) {
                IPersistentTeacher persistentTeacher = sp.getIPersistentTeacher();
                ITeacher teacher = persistentTeacher.readByNumber(infoGrantCostCenter
                        .getInfoResponsibleTeacher().getTeacherNumber());
                if (teacher == null)
                    throw new GrantOrientationTeacherNotFoundException();
                grantCostCenter.setResponsibleTeacher(teacher);
            }
        }
    }

    @Override
    protected IDomainObject createNewDomainObject(InfoObject infoObject) {
        if (infoObject.getClass().getName().equals(InfoGrantProject.class))
            return DomainFactory.makeGrantProject();
        if (infoObject.getClass().getName().equals(InfoGrantCostCenter.class))
            return DomainFactory.makeGrantCostCenter();

        return null;
    }

    @Override
    protected Class getDomainObjectClass() {
        return GrantPaymentEntity.class;
    }

    public void run(InfoGrantCostCenter infoObject) throws FenixServiceException {
        super.run(new Integer(0), infoObject);
    }

    public void run(InfoGrantProject infoObject) throws FenixServiceException {
        super.run(new Integer(0), infoObject);
    }

    @Override
    protected IPersistentObject getIPersistentObject(ISuportePersistente sp) {
        return sp.getIPersistentGrantPaymentEntity();
    }

}
