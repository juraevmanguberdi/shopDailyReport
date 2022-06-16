/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import DebtGivenDetailComponent from '@/entities/debt-given/debt-given-details.vue';
import DebtGivenClass from '@/entities/debt-given/debt-given-details.component';
import DebtGivenService from '@/entities/debt-given/debt-given.service';
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
  describe('DebtGiven Management Detail Component', () => {
    let wrapper: Wrapper<DebtGivenClass>;
    let comp: DebtGivenClass;
    let debtGivenServiceStub: SinonStubbedInstance<DebtGivenService>;

    beforeEach(() => {
      debtGivenServiceStub = sinon.createStubInstance<DebtGivenService>(DebtGivenService);

      wrapper = shallowMount<DebtGivenClass>(DebtGivenDetailComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: { debtGivenService: () => debtGivenServiceStub, alertService: () => new AlertService() },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundDebtGiven = { id: 123 };
        debtGivenServiceStub.find.resolves(foundDebtGiven);

        // WHEN
        comp.retrieveDebtGiven(123);
        await comp.$nextTick();

        // THEN
        expect(comp.debtGiven).toBe(foundDebtGiven);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundDebtGiven = { id: 123 };
        debtGivenServiceStub.find.resolves(foundDebtGiven);

        // WHEN
        comp.beforeRouteEnter({ params: { debtGivenId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.debtGiven).toBe(foundDebtGiven);
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
