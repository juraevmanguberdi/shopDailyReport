/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import AssetLiveComponent from '@/entities/asset-live/asset-live.vue';
import AssetLiveClass from '@/entities/asset-live/asset-live.component';
import AssetLiveService from '@/entities/asset-live/asset-live.service';
import AlertService from '@/shared/alert/alert.service';

const localVue = createLocalVue();
localVue.use(ToastPlugin);

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('b-badge', {});
localVue.component('jhi-sort-indicator', {});
localVue.directive('b-modal', {});
localVue.component('b-button', {});
localVue.component('router-link', {});

const bModalStub = {
  render: () => {},
  methods: {
    hide: () => {},
    show: () => {},
  },
};

describe('Component Tests', () => {
  describe('AssetLive Management Component', () => {
    let wrapper: Wrapper<AssetLiveClass>;
    let comp: AssetLiveClass;
    let assetLiveServiceStub: SinonStubbedInstance<AssetLiveService>;

    beforeEach(() => {
      assetLiveServiceStub = sinon.createStubInstance<AssetLiveService>(AssetLiveService);
      assetLiveServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<AssetLiveClass>(AssetLiveComponent, {
        store,
        i18n,
        localVue,
        stubs: { jhiItemCount: true, bPagination: true, bModal: bModalStub as any },
        provide: {
          assetLiveService: () => assetLiveServiceStub,
          alertService: () => new AlertService(),
        },
      });
      comp = wrapper.vm;
    });

    it('Should call load all on init', async () => {
      // GIVEN
      assetLiveServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllAssetLives();
      await comp.$nextTick();

      // THEN
      expect(assetLiveServiceStub.retrieve.called).toBeTruthy();
      expect(comp.assetLives[0]).toEqual(expect.objectContaining({ id: 123 }));
    });

    it('should load a page', async () => {
      // GIVEN
      assetLiveServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });
      comp.previousPage = 1;

      // WHEN
      comp.loadPage(2);
      await comp.$nextTick();

      // THEN
      expect(assetLiveServiceStub.retrieve.called).toBeTruthy();
      expect(comp.assetLives[0]).toEqual(expect.objectContaining({ id: 123 }));
    });

    it('should not load a page if the page is the same as the previous page', () => {
      // GIVEN
      assetLiveServiceStub.retrieve.reset();
      comp.previousPage = 1;

      // WHEN
      comp.loadPage(1);

      // THEN
      expect(assetLiveServiceStub.retrieve.called).toBeFalsy();
    });

    it('should re-initialize the page', async () => {
      // GIVEN
      assetLiveServiceStub.retrieve.reset();
      assetLiveServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.loadPage(2);
      await comp.$nextTick();
      comp.clear();
      await comp.$nextTick();

      // THEN
      expect(assetLiveServiceStub.retrieve.callCount).toEqual(3);
      expect(comp.page).toEqual(1);
      expect(comp.assetLives[0]).toEqual(expect.objectContaining({ id: 123 }));
    });

    it('should calculate the sort attribute for an id', () => {
      // WHEN
      const result = comp.sort();

      // THEN
      expect(result).toEqual(['id,asc']);
    });

    it('should calculate the sort attribute for a non-id attribute', () => {
      // GIVEN
      comp.propOrder = 'name';

      // WHEN
      const result = comp.sort();

      // THEN
      expect(result).toEqual(['name,asc', 'id']);
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      assetLiveServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      expect(assetLiveServiceStub.retrieve.callCount).toEqual(1);

      comp.removeAssetLive();
      await comp.$nextTick();

      // THEN
      expect(assetLiveServiceStub.delete.called).toBeTruthy();
      expect(assetLiveServiceStub.retrieve.callCount).toEqual(2);
    });
  });
});
