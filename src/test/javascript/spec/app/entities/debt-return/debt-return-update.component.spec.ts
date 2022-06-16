/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import DebtReturnUpdateComponent from '@/entities/debt-return/debt-return-update.vue';
import DebtReturnClass from '@/entities/debt-return/debt-return-update.component';
import DebtReturnService from '@/entities/debt-return/debt-return.service';

import ClientService from '@/entities/client/client.service';

import PaymentMethodService from '@/entities/payment-method/payment-method.service';
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
  describe('DebtReturn Management Update Component', () => {
    let wrapper: Wrapper<DebtReturnClass>;
    let comp: DebtReturnClass;
    let debtReturnServiceStub: SinonStubbedInstance<DebtReturnService>;

    beforeEach(() => {
      debtReturnServiceStub = sinon.createStubInstance<DebtReturnService>(DebtReturnService);

      wrapper = shallowMount<DebtReturnClass>(DebtReturnUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          debtReturnService: () => debtReturnServiceStub,
          alertService: () => new AlertService(),

          clientService: () =>
            sinon.createStubInstance<ClientService>(ClientService, {
              retrieve: sinon.stub().resolves({}),
            } as any),

          paymentMethodService: () =>
            sinon.createStubInstance<PaymentMethodService>(PaymentMethodService, {
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
        comp.debtReturn = entity;
        debtReturnServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(debtReturnServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.debtReturn = entity;
        debtReturnServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(debtReturnServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundDebtReturn = { id: 123 };
        debtReturnServiceStub.find.resolves(foundDebtReturn);
        debtReturnServiceStub.retrieve.resolves([foundDebtReturn]);

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
