/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import ExpenseDetailComponent from '@/entities/expense/expense-details.vue';
import ExpenseClass from '@/entities/expense/expense-details.component';
import ExpenseService from '@/entities/expense/expense.service';
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
  describe('Expense Management Detail Component', () => {
    let wrapper: Wrapper<ExpenseClass>;
    let comp: ExpenseClass;
    let expenseServiceStub: SinonStubbedInstance<ExpenseService>;

    beforeEach(() => {
      expenseServiceStub = sinon.createStubInstance<ExpenseService>(ExpenseService);

      wrapper = shallowMount<ExpenseClass>(ExpenseDetailComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: { expenseService: () => expenseServiceStub, alertService: () => new AlertService() },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundExpense = { id: 123 };
        expenseServiceStub.find.resolves(foundExpense);

        // WHEN
        comp.retrieveExpense(123);
        await comp.$nextTick();

        // THEN
        expect(comp.expense).toBe(foundExpense);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundExpense = { id: 123 };
        expenseServiceStub.find.resolves(foundExpense);

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
