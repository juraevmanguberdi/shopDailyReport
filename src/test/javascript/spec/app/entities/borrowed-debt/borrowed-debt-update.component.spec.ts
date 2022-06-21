/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import BorrowedDebtUpdateComponent from '@/entities/borrowed-debt/borrowed-debt-update.vue';
import BorrowedDebtClass from '@/entities/borrowed-debt/borrowed-debt-update.component';
import BorrowedDebtService from '@/entities/borrowed-debt/borrowed-debt.service';

import BorrowedDebtTypeService from '@/entities/borrowed-debt-type/borrowed-debt-type.service';
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
  describe('BorrowedDebt Management Update Component', () => {
    let wrapper: Wrapper<BorrowedDebtClass>;
    let comp: BorrowedDebtClass;
    let borrowedDebtServiceStub: SinonStubbedInstance<BorrowedDebtService>;

    beforeEach(() => {
      borrowedDebtServiceStub = sinon.createStubInstance<BorrowedDebtService>(BorrowedDebtService);

      wrapper = shallowMount<BorrowedDebtClass>(BorrowedDebtUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          borrowedDebtService: () => borrowedDebtServiceStub,
          alertService: () => new AlertService(),

          borrowedDebtTypeService: () =>
            sinon.createStubInstance<BorrowedDebtTypeService>(BorrowedDebtTypeService, {
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
        comp.borrowedDebt = entity;
        borrowedDebtServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(borrowedDebtServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.borrowedDebt = entity;
        borrowedDebtServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(borrowedDebtServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundBorrowedDebt = { id: 123 };
        borrowedDebtServiceStub.find.resolves(foundBorrowedDebt);
        borrowedDebtServiceStub.retrieve.resolves([foundBorrowedDebt]);

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
