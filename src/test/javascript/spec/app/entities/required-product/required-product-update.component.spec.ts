/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import RequiredProductUpdateComponent from '@/entities/required-product/required-product-update.vue';
import RequiredProductClass from '@/entities/required-product/required-product-update.component';
import RequiredProductService from '@/entities/required-product/required-product.service';

import RequiredProductTypeService from '@/entities/required-product-type/required-product-type.service';
import AlertService from '@/shared/alert/alert.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
const router = new Router();
localVue.use(Router);
localVue.use(ToastPlugin);
localVue.component('font-awesome-icon', {});
localVue.component('b-input-group', {});
localVue.component('b-input-group-prepend', {});
localVue.component('b-form-datepicker', {});
localVue.component('b-form-input', {});

describe('Component Tests', () => {
  describe('RequiredProduct Management Update Component', () => {
    let wrapper: Wrapper<RequiredProductClass>;
    let comp: RequiredProductClass;
    let requiredProductServiceStub: SinonStubbedInstance<RequiredProductService>;

    beforeEach(() => {
      requiredProductServiceStub = sinon.createStubInstance<RequiredProductService>(RequiredProductService);

      wrapper = shallowMount<RequiredProductClass>(RequiredProductUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          requiredProductService: () => requiredProductServiceStub,
          alertService: () => new AlertService(),

          requiredProductTypeService: () =>
            sinon.createStubInstance<RequiredProductTypeService>(RequiredProductTypeService, {
              retrieve: sinon.stub().resolves({}),
            } as any),
        },
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.requiredProduct = entity;
        requiredProductServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(requiredProductServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.requiredProduct = entity;
        requiredProductServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(requiredProductServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundRequiredProduct = { id: 123 };
        requiredProductServiceStub.find.resolves(foundRequiredProduct);
        requiredProductServiceStub.retrieve.resolves([foundRequiredProduct]);

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
