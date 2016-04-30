//package weight.tracker.controller;
//
//import javax.servlet.http.HttpServletResponse;
//
//public class MetricsControllerTest<MetricsController> {
//	MetricsController metricsController;
//
//	@Mock
//	MetricsController metricsController;
//	@Mock
//	HttpServletResponse httpServletResponse;
//	@Mock
//	
//	@Mock
//	UriInfo uriInfo;
//	@Mock
//	UriBuilder uriBuilder;
//
//	URI uri;
//	final String BASE_URI = "http://localhost/";
//	Validator validator;
//
//	@Before
//	public void before() throws Exception {
//		MockitoAnnotations.initMocks(this);
//
//	
//		when(uriInfo.getRequestUriBuilder()).thenReturn(uriBuilder);
//		class UriBuilderState {
//			Map<String, Object> map = new HashMap<String, Object>();
//		}
//		
//		final UriBuilderState uriBuilderState = new UriBuilderState();
//		when(uriBuilder.replaceQueryParam(anyString(), any())).thenAnswer(new Answer<UriBuilder>() {
//
//			@Override
//			public UriBuilder answer(final InvocationOnMock invocation) throws Throwable {
//				uriBuilderState.map.put((String) invocation.getArguments()[0], invocation.getArguments()[1]);
//				return (UriBuilder) invocation.getMock();
//			}
//		});
//		when(uriBuilder.build()).thenAnswer(new Answer<URI>() {
//
//			@Override
//			public URI answer(final InvocationOnMock invocation) throws Throwable {
//				String s = BASE_URI;
//				boolean first = true;
//				for (final Entry<String, Object> entry : uriBuilderState.map.entrySet()) {
//					if (first) {
//						s += "?";
//						first = false;
//					} else {
//						s += "&";
//					}
//					s += entry.getKey();
//					s += "=";
//					s += entry.getValue();
//				}
//				return new URI(s);
//			}
//		});
//	}
//
//	@Test
//	public void testGetLocationByIDs() throws Exception {
//		final URI uri = new URI(BASE_URI);
//		when(uriInfo.getAbsolutePath()).thenReturn(uri);
//
//	}
//}
