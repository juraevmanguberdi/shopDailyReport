/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import PaymentMethodUpdateComponent from '@/entities/payment-method/payment-method-update.vue';
import PaymentMethodClass from '@/entities/payment-method/payment-method-update.component';
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
  describe('PaymentMethod Management Update Component', () => {
    let wrapper: Wrapper<PaymentMethodClass>;
    let comp: PaymentMethodClass;
    let paymentMethodServiceStub: SinonStubbedInstance<PaymentMethodService>;

    beforeEach(() => {
      paymentMethodServiceStub = sinon.createStubInstance<PaymentMethodService>(PaymentMethodService);

      wrapper = shallowMount<PaymentMethodClass>(PaymentMethodUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          paymentMethodService: () => paymentMethodServiceStub,
          alertService: () => new AlertService(),
        },
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.paymentMethod = entity;
        paymentMethodServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(paymentMethodServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.paymentMethod = entity;
        paymentMethodServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(paymentMethodServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundPaymentMethod = { id: 123 };
        paymentMethodServiceStub.find.resolves(foundPaymentMethod);
        paymentMethodServiceStub.retrieve.resolves([foundPaymentMethod]);

        // WHEN
        comp.beforeRouteEnter({ params: { paymentMethodId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.paymentMethod).toBe(foundPaymentMethod);
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
