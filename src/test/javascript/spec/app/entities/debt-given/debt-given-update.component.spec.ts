/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import DebtGivenUpdateComponent from '@/entities/debt-given/debt-given-update.vue';
import DebtGivenClass from '@/entities/debt-given/debt-given-update.component';
import DebtGivenService from '@/entities/debt-given/debt-given.service';

import ClientService from '@/entities/client/client.service';
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
  describe('DebtGiven Management Update Component', () => {
    let wrapper: Wrapper<DebtGivenClass>;
    let comp: DebtGivenClass;
    let debtGivenServiceStub: SinonStubbedInstance<DebtGivenService>;

    beforeEach(() => {
      debtGivenServiceStub = sinon.createStubInstance<DebtGivenService>(DebtGivenService);

      wrapper = shallowMount<DebtGivenClass>(DebtGivenUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          debtGivenService: () => debtGivenServiceStub,
          alertService: () => new AlertService(),

          clientService: () =>
            sinon.createStubInstance<ClientService>(ClientService, {
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
        comp.debtGiven = entity;
        debtGivenServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(debtGivenServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.debtGiven = entity;
        debtGivenServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(debtGivenServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundDebtGiven = { id: 123 };
        debtGivenServiceStub.find.resolves(foundDebtGiven);
        debtGivenServiceStub.retrieve.resolves([foundDebtGiven]);

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
