/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import AssetLiveUpdateComponent from '@/entities/asset-live/asset-live-update.vue';
import AssetLiveClass from '@/entities/asset-live/asset-live-update.component';
import AssetLiveService from '@/entities/asset-live/asset-live.service';

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
  describe('AssetLive Management Update Component', () => {
    let wrapper: Wrapper<AssetLiveClass>;
    let comp: AssetLiveClass;
    let assetLiveServiceStub: SinonStubbedInstance<AssetLiveService>;

    beforeEach(() => {
      assetLiveServiceStub = sinon.createStubInstance<AssetLiveService>(AssetLiveService);

      wrapper = shallowMount<AssetLiveClass>(AssetLiveUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          assetLiveService: () => assetLiveServiceStub,
          alertService: () => new AlertService(),
        },
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.assetLive = entity;
        assetLiveServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(assetLiveServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.assetLive = entity;
        assetLiveServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(assetLiveServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundAssetLive = { id: 123 };
        assetLiveServiceStub.find.resolves(foundAssetLive);
        assetLiveServiceStub.retrieve.resolves([foundAssetLive]);

        // WHEN
        comp.beforeRouteEnter({ params: { assetLiveId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.assetLive).toBe(foundAssetLive);
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
