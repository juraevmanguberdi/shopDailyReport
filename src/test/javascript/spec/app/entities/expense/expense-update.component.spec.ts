/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import ExpenseUpdateComponent from '@/entities/expense/expense-update.vue';
import ExpenseClass from '@/entities/expense/expense-update.component';
import ExpenseService from '@/entities/expense/expense.service';

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
  describe('Expense Management Update Component', () => {
    let wrapper: Wrapper<ExpenseClass>;
    let comp: ExpenseClass;
    let expenseServiceStub: SinonStubbedInstance<ExpenseService>;

    beforeEach(() => {
      expenseServiceStub = sinon.createStubInstance<ExpenseService>(ExpenseService);

      wrapper = shallowMount<ExpenseClass>(ExpenseUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          expenseService: () => expenseServiceStub,
          alertService: () => new AlertService(),

          expenseTypeService: () =>
            sinon.createStubInstance<ExpenseTypeService>(ExpenseTypeService, {
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
        comp.expense = entity;
        expenseServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(expenseServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.expense = entity;
        expenseServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(expenseServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundExpense = { id: 123 };
        expenseServiceStub.find.resolves(foundExpense);
        expenseServiceStub.retrieve.resolves([foundExpense]);

        // WHEN
        comp.beforeRouteEnter({ params: { expenseId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.expense).toBe(foundExpense);
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
