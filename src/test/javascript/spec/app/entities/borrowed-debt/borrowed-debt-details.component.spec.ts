/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import BorrowedDebtDetailComponent from '@/entities/borrowed-debt/borrowed-debt-details.vue';
import BorrowedDebtClass from '@/entities/borrowed-debt/borrowed-debt-details.component';
import BorrowedDebtService from '@/entities/borrowed-debt/borrowed-debt.service';
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
  describe('BorrowedDebt Management Detail Component', () => {
    let wrapper: Wrapper<BorrowedDebtClass>;
    let comp: BorrowedDebtClass;
    let borrowedDebtServiceStub: SinonStubbedInstance<BorrowedDebtService>;

    beforeEach(() => {
      borrowedDebtServiceStub = sinon.createStubInstance<BorrowedDebtService>(BorrowedDebtService);

      wrapper = shallowMount<BorrowedDebtClass>(BorrowedDebtDetailComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: { borrowedDebtService: () => borrowedDebtServiceStub, alertService: () => new AlertService() },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundBorrowedDebt = { id: 123 };
        borrowedDebtServiceStub.find.resolves(foundBorrowedDebt);

        // WHEN
        comp.retrieveBorrowedDebt(123);
        await comp.$nextTick();

        // THEN
        expect(comp.borrowedDebt).toBe(foundBorrowedDebt);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundBorrowedDebt = { id: 123 };
        borrowedDebtServiceStub.find.resolves(foundBorrowedDebt);

        // WHEN
        comp.beforeRouteEnter({ params: { borrowedDebtId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.borrowedDebt).toBe(foundBorrowedDebt);
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
