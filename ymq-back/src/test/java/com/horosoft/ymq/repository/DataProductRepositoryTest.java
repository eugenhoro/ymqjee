package com.horosoft.ymq.repository;

/**
 * Created by eugen on 7/11/2017.
 */

import com.horosoft.ymq.model.DataProduct;
import com.horosoft.ymq.model.ProductType;
import com.horosoft.ymq.utils.IsbnGenerator;
import com.horosoft.ymq.utils.NumberGenerator;
import com.horosoft.ymq.utils.TextUtil;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.junit.InSequence;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;
import java.util.Date;

import static org.junit.Assert.*;

@RunWith(Arquillian.class)
public class DataProductRepositoryTest {

    // ======================================
    // =             Attributes             =
    // ======================================

    private static Long dataProductId;

    // ======================================
    // =          Injection Points          =
    // ======================================

    @Inject
    private DataProductRepository dataProductRepository;

    @Inject
    private IsbnGenerator isbnGenerator;

    @Inject
    private TextUtil textUtil;

    // ======================================
    // =             Deployment             =
    // ======================================

    @Deployment
    public static Archive<?> createDeploymentPackage() {

        return ShrinkWrap.create(JavaArchive.class)
                .addClass(DataProduct.class)
                .addClass(ProductType.class)
                .addClass(NumberGenerator.class)
                .addClass(TextUtil.class)
                .addClass(IsbnGenerator.class)
                .addClass(DataProductRepository.class)
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml")
                .addAsManifestResource("META-INF/test-persistence.xml", "persistence.xml");
    }

    // ======================================
    // =            Test methods            =
    // ======================================

    @Test
    @InSequence(1)
    public void shouldBeDeployed() {
        assertNotNull(dataProductRepository);
        assertNotNull(isbnGenerator);
        assertNotNull(textUtil);
    }

    @Test
    @InSequence(2)
    public void shouldGetNoDataProduct() {
        // Count all
        assertEquals(Long.valueOf(0), dataProductRepository.countAll());
        // Find all
        assertEquals(0, dataProductRepository.findAll().size());
    }

    @Test
    @InSequence(3)
    public void shouldCreateADataProduct() {
        // Creates a DataProduct
        DataProduct DataProduct = new DataProduct("isbn", "a   title", 12F, 123, ProductType.CAR, new Date(), "imageURL", "description");
        DataProduct = dataProductRepository.create(DataProduct);
        // Checks the created DataProduct
        assertNotNull(DataProduct);
        assertNotNull(DataProduct.getId());
        dataProductId = DataProduct.getId();
    }

    @Test
    @InSequence(4)
    public void shouldFindTheCreatedDataProduct() {
        // Finds the dataProduct
        DataProduct dataProductFound = dataProductRepository.find(dataProductId);
        // Checks the found book
        assertNotNull(dataProductFound.getId());
        assertTrue(dataProductFound.getIsbn().startsWith("13-84356-"));
        assertEquals("a title", dataProductFound.getTitle());
    }

    @Test
    @InSequence(5)
    public void shouldGetOneDataProduct() {
        // Count all
        assertEquals(Long.valueOf(1), dataProductRepository.countAll());
        // Find all
        assertEquals(1, dataProductRepository.findAll().size());
    }

    @Test
    @InSequence(6)
    public void shouldDeleteTheCreatedDataProduct() {
        // Deletes the DataProduct
        dataProductRepository.delete(dataProductId);
        // Checks the deleted DataProduct
        DataProduct DataProductDeleted = dataProductRepository.find(dataProductId);
        assertNull(DataProductDeleted);
    }

    @Test
    @InSequence(7)
    public void shouldGetNoMoreDataProduct() {
        // Count all
        assertEquals(Long.valueOf(0), dataProductRepository.countAll());
        // Find all
        assertEquals(0, dataProductRepository.findAll().size());
    }

    @Test(expected = Exception.class)
    @InSequence(10)
    public void shouldFailCreatingANullDataProduct() {
        dataProductRepository.create(null);
    }

    @Test(expected = Exception.class)
    @InSequence(11)
    public void shouldFailCreatingADataProductWithNullTitle() {
        dataProductRepository.create(new DataProduct("isbn", null, 12F, 123, ProductType.CAR, new Date(), "imageURL", "description"));
    }

    @Test(expected = Exception.class)
    @InSequence(12)
    public void shouldFailCreatingADataProductWithLowUnitCostTitle() {
        dataProductRepository.create(new DataProduct("isbn", "title", 0F, 123, ProductType.CAR, new Date(), "imageURL", "description"));
    }

    @Test
    @InSequence(13)
    public void shouldNotFailCreatingABookWithNullISBN() {
        DataProduct dataProductFound = dataProductRepository.create(new DataProduct(null, "title", 12F, 123, ProductType.CAR, new Date(), "imageURL", "description"));
        assertTrue(dataProductFound.getIsbn().startsWith("13-84356-"));
    }

    @Test(expected = Exception.class)
    @InSequence(14)
    public void shouldFailInvokingFindByIdWithNull() {
        dataProductRepository.find(null);
    }

    @Test
    @InSequence(15)
    public void shouldNotFindUnknownId() {
        assertNull(dataProductRepository.find(99999L));
    }

    @Test(expected = Exception.class)
    @InSequence(16)
    public void shouldFailInvokingDeleteByIdWithNull() {
        dataProductRepository.delete(null);
    }
    @Test(expected = Exception.class)
    @InSequence(17)
    public void shouldNotDeleteUnknownId() {
        dataProductRepository.delete(99999L);
    }

}
