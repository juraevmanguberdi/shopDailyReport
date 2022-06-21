/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import BorrowedDebtTypeUpdateComponent from '@/entities/borrowed-debt-type/borrowed-debt-type-update.vue';
import BorrowedDebtTypeClass from '@/entities/borrowed-debt-type/borrowed-debt-type-update.component';
import BorrowedDebtTypeService from '@/entities/borrowed-debt-type/borrowed-debt-type.service';

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
  describe('BorrowedDebtType Management Update Component', () => {
    let wrapper: Wrapper<BorrowedDebtTypeClass>;
    let comp: BorrowedDebtTypeClass;
    let borrowedDebtTypeServiceStub: SinonStubbedInstance<BorrowedDebtTypeService>;

    beforeEach(() => {
      borrowedDebtTypeServiceStub = sinon.createStubInstance<BorrowedDebtTypeService>(BorrowedDebtTypeService);

      wrapper = shallowMount<BorrowedDebtTypeClass>(BorrowedDebtTypeUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          borrowedDebtTypeService: () => borrowedDebtTypeServiceStub,
          alertService: () => new AlertService(),
        },
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.borrowedDebtType = entity;
        borrowedDebtTypeServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(borrowedDebtTypeServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.borrowedDebtType = entity;
        borrowedDebtTypeServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(borrowedDebtTypeServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundBorrowedDebtType = { id: 123 };
        borrowedDebtTypeServiceStub.find.resolves(foundBorrowedDebtType);
        borrowedDebtTypeServiceStub.retrieve.resolves([foundBorrowedDebtType]);

        // WHEN
        comp.beforeRouteEnter({ params: { borrowedDebtTypeId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.borrowedDebtType).toBe(foundBorrowedDebtType);
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
