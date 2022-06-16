/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import DebtReturnDetailComponent from '@/entities/debt-return/debt-return-details.vue';
import DebtReturnClass from '@/entities/debt-return/debt-return-details.component';
import DebtReturnService from '@/entities/debt-return/debt-return.service';
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
  describe('DebtReturn Management Detail Component', () => {
    let wrapper: Wrapper<DebtReturnClass>;
    let comp: DebtReturnClass;
    let debtReturnServiceStub: SinonStubbedInstance<DebtReturnService>;

    beforeEach(() => {
      debtReturnServiceStub = sinon.createStubInstance<DebtReturnService>(DebtReturnService);

      wrapper = shallowMount<DebtReturnClass>(DebtReturnDetailComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: { debtReturnService: () => debtReturnServiceStub, alertService: () => new AlertService() },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundDebtReturn = { id: 123 };
        debtReturnServiceStub.find.resolves(foundDebtReturn);

        // WHEN
        comp.retrieveDebtReturn(123);
        await comp.$nextTick();

        // THEN
        expect(comp.debtReturn).toBe(foundDebtReturn);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundDebtReturn = { id: 123 };
        debtReturnServiceStub.find.resolves(foundDebtReturn);

        // WHEN
        comp.beforeRouteEnter({ params: { debtReturnId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.debtReturn).toBe(foundDebtReturn);
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
