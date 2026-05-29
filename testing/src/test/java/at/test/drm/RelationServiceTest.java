package at.test.drm;

import at.drm.factory.RelationDaoFactory;
import at.drm.model.RelationLink;
import at.drm.service.RelationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
public class RelationServiceTest {

    @Mock
    private RelationDaoFactory relationDaoFactory;

    @Mock
    private PersonEntityRelationDao personEntityRelationDao;

    @InjectMocks
    private RelationService relationService;

    @Test
    void createRelation() {
        Mockito.when(relationDaoFactory.getDaoFromSourceObjectClass(any(Class.class)))
                .thenReturn(personEntityRelationDao);
        Mockito.when(personEntityRelationDao.save(any()))
                .thenReturn(new PersonEntityRelation());
        PersonEntity PersonEntity = new PersonEntity();
        PersonEntity.setId(1L);
        DogEntity DogEntity = new DogEntity();
        DogEntity.setId(1L);
        RelationLink result = relationService.createRelation(PersonEntity, DogEntity);

        assertThat(result).isNotNull();
    }

    @Test
    void deleteRelation() {
        Mockito.when(relationDaoFactory.getDaoFromSourceObjectClass(any(Class.class)))
                .thenReturn(personEntityRelationDao);
        PersonEntityRelation personEntityRelation = new PersonEntityRelation();
        personEntityRelation.setSourceObject(new PersonEntity());
        relationService.deleteRelation(personEntityRelation);

        verify(personEntityRelationDao, times(1)).delete(personEntityRelation);
    }

    @Test
    void findRelationBySourceObject() {
        Mockito.when(relationDaoFactory.getDaoFromSourceObjectClass(any(Class.class)))
                .thenReturn(personEntityRelationDao);
        PersonEntity PersonEntity = new PersonEntity();
        PersonEntity.setId(1L);
        List<RelationLink> relationBySourceObject = relationService.findRelationBySourceObject(PersonEntity);

        assertThat(relationBySourceObject).isNotNull();
    }

    @Test
    void findRelationByTargetRelationIdentity() {
        Mockito.when(relationDaoFactory.getAllDaos())
                .thenReturn(Collections.singleton(personEntityRelationDao));
        PersonEntity PersonEntity = new PersonEntity();
        PersonEntity.setId(1L);
        Set<RelationLink> relationByTargetRelationIdentity = relationService.findRelationByTargetRelationIdentity(PersonEntity);

        assertThat(relationByTargetRelationIdentity).isNotNull();
    }

}