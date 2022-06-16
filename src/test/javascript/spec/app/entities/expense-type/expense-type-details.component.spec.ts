/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import ExpenseTypeDetailComponent from '@/entities/expense-type/expense-type-details.vue';
import ExpenseTypeClass from '@/entities/expense-type/expense-type-details.component';
import ExpenseTypeService from '@/entities/expense-type/expense-type.service';
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
  describe('ExpenseType Management Detail Component', () => {
    let wrapper: Wrapper<ExpenseTypeClass>;
    let comp: ExpenseTypeClass;
    let expenseTypeServiceStub: SinonStubbedInstance<ExpenseTypeService>;

    beforeEach(() => {
      expenseTypeServiceStub = sinon.createStubInstance<ExpenseTypeService>(ExpenseTypeService);

      wrapper = shallowMount<ExpenseTypeClass>(ExpenseTypeDetailComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: { expenseTypeService: () => expenseTypeServiceStub, alertService: () => new AlertService() },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundExpenseType = { id: 123 };
        expenseTypeServiceStub.find.resolves(foundExpenseType);

        // WHEN
        comp.retrieveExpenseType(123);
        await comp.$nextTick();

        // THEN
        expect(comp.expenseType).toBe(foundExpenseType);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundExpenseType = { id: 123 };
        expenseTypeServiceStub.find.resolves(foundExpenseType);

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
