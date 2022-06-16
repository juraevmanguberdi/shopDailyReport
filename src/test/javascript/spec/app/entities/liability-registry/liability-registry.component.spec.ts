/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import LiabilityRegistryComponent from '@/entities/liability-registry/liability-registry.vue';
import LiabilityRegistryClass from '@/entities/liability-registry/liability-registry.component';
import LiabilityRegistryService from '@/entities/liability-registry/liability-registry.service';
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
  describe('LiabilityRegistry Management Component', () => {
    let wrapper: Wrapper<LiabilityRegistryClass>;
    let comp: LiabilityRegistryClass;
    let liabilityRegistryServiceStub: SinonStubbedInstance<LiabilityRegistryService>;

    beforeEach(() => {
      liabilityRegistryServiceStub = sinon.createStubInstance<LiabilityRegistryService>(LiabilityRegistryService);
      liabilityRegistryServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<LiabilityRegistryClass>(LiabilityRegistryComponent, {
        store,
        i18n,
        localVue,
        stubs: { jhiItemCount: true, bPagination: true, bModal: bModalStub as any },
        provide: {
          liabilityRegistryService: () => liabilityRegistryServiceStub,
          alertService: () => new AlertService(),
        },
      });
      comp = wrapper.vm;
    });

    it('Should call load all on init', async () => {
      // GIVEN
      liabilityRegistryServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllLiabilityRegistrys();
      await comp.$nextTick();

      // THEN
      expect(liabilityRegistryServiceStub.retrieve.called).toBeTruthy();
      expect(comp.liabilityRegistries[0]).toEqual(expect.objectContaining({ id: 123 }));
    });

    it('should load a page', async () => {
      // GIVEN
      liabilityRegistryServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });
      comp.previousPage = 1;

      // WHEN
      comp.loadPage(2);
      await comp.$nextTick();

      // THEN
      expect(liabilityRegistryServiceStub.retrieve.called).toBeTruthy();
      expect(comp.liabilityRegistries[0]).toEqual(expect.objectContaining({ id: 123 }));
    });

    it('should not load a page if the page is the same as the previous page', () => {
      // GIVEN
      liabilityRegistryServiceStub.retrieve.reset();
      comp.previousPage = 1;

      // WHEN
      comp.loadPage(1);

      // THEN
      expect(liabilityRegistryServiceStub.retrieve.called).toBeFalsy();
    });

    it('should re-initialize the page', async () => {
      // GIVEN
      liabilityRegistryServiceStub.retrieve.reset();
      liabilityRegistryServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.loadPage(2);
      await comp.$nextTick();
      comp.clear();
      await comp.$nextTick();

      // THEN
      expect(liabilityRegistryServiceStub.retrieve.callCount).toEqual(3);
      expect(comp.page).toEqual(1);
      expect(comp.liabilityRegistries[0]).toEqual(expect.objectContaining({ id: 123 }));
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
      liabilityRegistryServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      expect(liabilityRegistryServiceStub.retrieve.callCount).toEqual(1);

      comp.removeLiabilityRegistry();
      await comp.$nextTick();

      // THEN
      expect(liabilityRegistryServiceStub.delete.called).toBeTruthy();
      expect(liabilityRegistryServiceStub.retrieve.callCount).toEqual(2);
    });
  });
});
