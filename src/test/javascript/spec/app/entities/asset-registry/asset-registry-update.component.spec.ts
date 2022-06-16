/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import AssetRegistryUpdateComponent from '@/entities/asset-registry/asset-registry-update.vue';
import AssetRegistryClass from '@/entities/asset-registry/asset-registry-update.component';
import AssetRegistryService from '@/entities/asset-registry/asset-registry.service';

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
  describe('AssetRegistry Management Update Component', () => {
    let wrapper: Wrapper<AssetRegistryClass>;
    let comp: AssetRegistryClass;
    let assetRegistryServiceStub: SinonStubbedInstance<AssetRegistryService>;

    beforeEach(() => {
      assetRegistryServiceStub = sinon.createStubInstance<AssetRegistryService>(AssetRegistryService);

      wrapper = shallowMount<AssetRegistryClass>(AssetRegistryUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          assetRegistryService: () => assetRegistryServiceStub,
          alertService: () => new AlertService(),
        },
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.assetRegistry = entity;
        assetRegistryServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(assetRegistryServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.assetRegistry = entity;
        assetRegistryServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(assetRegistryServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundAssetRegistry = { id: 123 };
        assetRegistryServiceStub.find.resolves(foundAssetRegistry);
        assetRegistryServiceStub.retrieve.resolves([foundAssetRegistry]);

        // WHEN
        comp.beforeRouteEnter({ params: { assetRegistryId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.assetRegistry).toBe(foundAssetRegistry);
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
