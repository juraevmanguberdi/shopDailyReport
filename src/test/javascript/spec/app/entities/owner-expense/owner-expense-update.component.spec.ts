/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import OwnerExpenseUpdateComponent from '@/entities/owner-expense/owner-expense-update.vue';
import OwnerExpenseClass from '@/entities/owner-expense/owner-expense-update.component';
import OwnerExpenseService from '@/entities/owner-expense/owner-expense.service';

import OwnerExpenseTypeService from '@/entities/owner-expense-type/owner-expense-type.service';
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
  describe('OwnerExpense Management Update Component', () => {
    let wrapper: Wrapper<OwnerExpenseClass>;
    let comp: OwnerExpenseClass;
    let ownerExpenseServiceStub: SinonStubbedInstance<OwnerExpenseService>;

    beforeEach(() => {
      ownerExpenseServiceStub = sinon.createStubInstance<OwnerExpenseService>(OwnerExpenseService);

      wrapper = shallowMount<OwnerExpenseClass>(OwnerExpenseUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          ownerExpenseService: () => ownerExpenseServiceStub,
          alertService: () => new AlertService(),

          ownerExpenseTypeService: () =>
            sinon.createStubInstance<OwnerExpenseTypeService>(OwnerExpenseTypeService, {
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
        comp.ownerExpense = entity;
        ownerExpenseServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(ownerExpenseServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.ownerExpense = entity;
        ownerExpenseServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(ownerExpenseServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundOwnerExpense = { id: 123 };
        ownerExpenseServiceStub.find.resolves(foundOwnerExpense);
        ownerExpenseServiceStub.retrieve.resolves([foundOwnerExpense]);

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
