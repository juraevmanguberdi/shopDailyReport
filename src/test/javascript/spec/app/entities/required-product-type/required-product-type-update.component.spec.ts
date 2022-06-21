/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import RequiredProductTypeUpdateComponent from '@/entities/required-product-type/required-product-type-update.vue';
import RequiredProductTypeClass from '@/entities/required-product-type/required-product-type-update.component';
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
  describe('RequiredProductType Management Update Component', () => {
    let wrapper: Wrapper<RequiredProductTypeClass>;
    let comp: RequiredProductTypeClass;
    let requiredProductTypeServiceStub: SinonStubbedInstance<RequiredProductTypeService>;

    beforeEach(() => {
      requiredProductTypeServiceStub = sinon.createStubInstance<RequiredProductTypeService>(RequiredProductTypeService);

      wrapper = shallowMount<RequiredProductTypeClass>(RequiredProductTypeUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          requiredProductTypeService: () => requiredProductTypeServiceStub,
          alertService: () => new AlertService(),
        },
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.requiredProductType = entity;
        requiredProductTypeServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(requiredProductTypeServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.requiredProductType = entity;
        requiredProductTypeServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(requiredProductTypeServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundRequiredProductType = { id: 123 };
        requiredProductTypeServiceStub.find.resolves(foundRequiredProductType);
        requiredProductTypeServiceStub.retrieve.resolves([foundRequiredProductType]);

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
