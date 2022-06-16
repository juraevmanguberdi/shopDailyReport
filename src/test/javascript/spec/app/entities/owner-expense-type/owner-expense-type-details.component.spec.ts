/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import OwnerExpenseTypeDetailComponent from '@/entities/owner-expense-type/owner-expense-type-details.vue';
import OwnerExpenseTypeClass from '@/entities/owner-expense-type/owner-expense-type-details.component';
import OwnerExpenseTypeService from '@/entities/owner-expense-type/owner-expense-type.service';
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
  describe('OwnerExpenseType Management Detail Component', () => {
    let wrapper: Wrapper<OwnerExpenseTypeClass>;
    let comp: OwnerExpenseTypeClass;
    let ownerExpenseTypeServiceStub: SinonStubbedInstance<OwnerExpenseTypeService>;

    beforeEach(() => {
      ownerExpenseTypeServiceStub = sinon.createStubInstance<OwnerExpenseTypeService>(OwnerExpenseTypeService);

      wrapper = shallowMount<OwnerExpenseTypeClass>(OwnerExpenseTypeDetailComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: { ownerExpenseTypeService: () => ownerExpenseTypeServiceStub, alertService: () => new AlertService() },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundOwnerExpenseType = { id: 123 };
        ownerExpenseTypeServiceStub.find.resolves(foundOwnerExpenseType);

        // WHEN
        comp.retrieveOwnerExpenseType(123);
        await comp.$nextTick();

        // THEN
        expect(comp.ownerExpenseType).toBe(foundOwnerExpenseType);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundOwnerExpenseType = { id: 123 };
        ownerExpenseTypeServiceStub.find.resolves(foundOwnerExpenseType);

        // WHEN
        comp.beforeRouteEnter({ params: { ownerExpenseTypeId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.ownerExpenseType).toBe(foundOwnerExpenseType);
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
