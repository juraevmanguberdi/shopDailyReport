/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import RequiredProductTypeDetailComponent from '@/entities/required-product-type/required-product-type-details.vue';
import RequiredProductTypeClass from '@/entities/required-product-type/required-product-type-details.component';
import RequiredProductTypeService from '@/entities/required-product-type/required-product-type.service';
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
  describe('RequiredProductType Management Detail Component', () => {
    let wrapper: Wrapper<RequiredProductTypeClass>;
    let comp: RequiredProductTypeClass;
    let requiredProductTypeServiceStub: SinonStubbedInstance<RequiredProductTypeService>;

    beforeEach(() => {
      requiredProductTypeServiceStub = sinon.createStubInstance<RequiredProductTypeService>(RequiredProductTypeService);

      wrapper = shallowMount<RequiredProductTypeClass>(RequiredProductTypeDetailComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: { requiredProductTypeService: () => requiredProductTypeServiceStub, alertService: () => new AlertService() },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundRequiredProductType = { id: 123 };
        requiredProductTypeServiceStub.find.resolves(foundRequiredProductType);

        // WHEN
        comp.retrieveRequiredProductType(123);
        await comp.$nextTick();

        // THEN
        expect(comp.requiredProductType).toBe(foundRequiredProductType);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundRequiredProductType = { id: 123 };
        requiredProductTypeServiceStub.find.resolves(foundRequiredProductType);

        // WHEN
        comp.beforeRouteEnter({ params: { requiredProductTypeId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.requiredProductType).toBe(foundRequiredProductType);
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
