/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import DailyRegistryShopUpdateComponent from '@/entities/daily-registry-shop/daily-registry-shop-update.vue';
import DailyRegistryShopClass from '@/entities/daily-registry-shop/daily-registry-shop-update.component';
import DailyRegistryShopService from '@/entities/daily-registry-shop/daily-registry-shop.service';

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
  describe('DailyRegistryShop Management Update Component', () => {
    let wrapper: Wrapper<DailyRegistryShopClass>;
    let comp: DailyRegistryShopClass;
    let dailyRegistryShopServiceStub: SinonStubbedInstance<DailyRegistryShopService>;

    beforeEach(() => {
      dailyRegistryShopServiceStub = sinon.createStubInstance<DailyRegistryShopService>(DailyRegistryShopService);

      wrapper = shallowMount<DailyRegistryShopClass>(DailyRegistryShopUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          dailyRegistryShopService: () => dailyRegistryShopServiceStub,
          alertService: () => new AlertService(),
        },
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.dailyRegistryShop = entity;
        dailyRegistryShopServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(dailyRegistryShopServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.dailyRegistryShop = entity;
        dailyRegistryShopServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(dailyRegistryShopServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundDailyRegistryShop = { id: 123 };
        dailyRegistryShopServiceStub.find.resolves(foundDailyRegistryShop);
        dailyRegistryShopServiceStub.retrieve.resolves([foundDailyRegistryShop]);

        // WHEN
        comp.beforeRouteEnter({ params: { dailyRegistryShopId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.dailyRegistryShop).toBe(foundDailyRegistryShop);
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
