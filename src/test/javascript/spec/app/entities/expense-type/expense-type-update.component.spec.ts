/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import ExpenseTypeUpdateComponent from '@/entities/expense-type/expense-type-update.vue';
import ExpenseTypeClass from '@/entities/expense-type/expense-type-update.component';
import ExpenseTypeService from '@/entities/expense-type/expense-type.service';

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
  describe('ExpenseType Management Update Component', () => {
    let wrapper: Wrapper<ExpenseTypeClass>;
    let comp: ExpenseTypeClass;
    let expenseTypeServiceStub: SinonStubbedInstance<ExpenseTypeService>;

    beforeEach(() => {
      expenseTypeServiceStub = sinon.createStubInstance<ExpenseTypeService>(ExpenseTypeService);

      wrapper = shallowMount<ExpenseTypeClass>(ExpenseTypeUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          expenseTypeService: () => expenseTypeServiceStub,
          alertService: () => new AlertService(),
        },
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.expenseType = entity;
        expenseTypeServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(expenseTypeServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.expenseType = entity;
        expenseTypeServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(expenseTypeServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundExpenseType = { id: 123 };
        expenseTypeServiceStub.find.resolves(foundExpenseType);
        expenseTypeServiceStub.retrieve.resolves([foundExpenseType]);

        // WHEN
        comp.beforeRouteEnter({ params: { expenseTypeId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.expenseType).toBe(foundExpenseType);
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
