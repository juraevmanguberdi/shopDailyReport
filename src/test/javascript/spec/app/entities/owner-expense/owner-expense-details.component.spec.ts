/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import OwnerExpenseDetailComponent from '@/entities/owner-expense/owner-expense-details.vue';
import OwnerExpenseClass from '@/entities/owner-expense/owner-expense-details.component';
import OwnerExpenseService from '@/entities/owner-expense/owner-expense.service';
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
  describe('OwnerExpense Management Detail Component', () => {
    let wrapper: Wrapper<OwnerExpenseClass>;
    let comp: OwnerExpenseClass;
    let ownerExpenseServiceStub: SinonStubbedInstance<OwnerExpenseService>;

    beforeEach(() => {
      ownerExpenseServiceStub = sinon.createStubInstance<OwnerExpenseService>(OwnerExpenseService);

      wrapper = shallowMount<OwnerExpenseClass>(OwnerExpenseDetailComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: { ownerExpenseService: () => ownerExpenseServiceStub, alertService: () => new AlertService() },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundOwnerExpense = { id: 123 };
        ownerExpenseServiceStub.find.resolves(foundOwnerExpense);

        // WHEN
        comp.retrieveOwnerExpense(123);
        await comp.$nextTick();

        // THEN
        expect(comp.ownerExpense).toBe(foundOwnerExpense);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundOwnerExpense = { id: 123 };
        ownerExpenseServiceStub.find.resolves(foundOwnerExpense);

        // WHEN
        comp.beforeRouteEnter({ params: { ownerExpenseId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.ownerExpense).toBe(foundOwnerExpense);
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
