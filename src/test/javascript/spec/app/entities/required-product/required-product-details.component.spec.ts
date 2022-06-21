/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import RequiredProductDetailComponent from '@/entities/required-product/required-product-details.vue';
import RequiredProductClass from '@/entities/required-product/required-product-details.component';
import RequiredProductService from '@/entities/required-product/required-product.service';
import router from '@/router';
import AlertService from '@/shared/alert/alert.service';

const localVue = createLocalVue();
localVue.use(VueRouter);

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('RequiredProduct Management Detail Component', () => {
    let wrapper: Wrapper<RequiredProductClass>;
    let comp: RequiredProductClass;
    let requiredProductServiceStub: SinonStubbedInstance<RequiredProductService>;

    beforeEach(() => {
      requiredProductServiceStub = sinon.createStubInstance<RequiredProductService>(RequiredProductService);

      wrapper = shallowMount<RequiredProductClass>(RequiredProductDetailComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: { requiredProductService: () => requiredProductServiceStub, alertService: () => new AlertService() },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundRequiredProduct = { id: 123 };
        requiredProductServiceStub.find.resolves(foundRequiredProduct);

        // WHEN
        comp.retrieveRequiredProduct(123);
        await comp.$nextTick();

        // THEN
        expect(comp.requiredProduct).toBe(foundRequiredProduct);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundRequiredProduct = { id: 123 };
        requiredProductServiceStub.find.resolves(foundRequiredProduct);

        // WHEN
        comp.beforeRouteEnter({ params: { requiredProductId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.requiredProduct).toBe(foundRequiredProduct);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        comp.previousState();
        await comp.$nextTick();

        expect(comp.$router.currentRoute.fullPath).toContain('/');
      });
    });
  });
});
